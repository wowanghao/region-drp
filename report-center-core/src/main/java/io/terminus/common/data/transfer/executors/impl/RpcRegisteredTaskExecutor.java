package io.terminus.common.data.transfer.executors.impl;

import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.ExcelColumnProperty;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.util.TypeUtil;
import com.alibaba.excel.util.WorkBookUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.terminus.boot.rpc.dubbo.light.common.DubboProperties;
import io.terminus.common.data.transfer.api.model.DataTransferTask;
import io.terminus.common.data.transfer.api.model.DataTransferTaskStatus;
import io.terminus.common.data.transfer.api.model.DataTransferTaskType;
import io.terminus.common.data.transfer.api.model.FileType;
import io.terminus.common.data.transfer.api.service.executor.IDataTransferTaskExecutor;
import io.terminus.common.data.transfer.api.facade.IDataTransferTaskManageFacade;
import io.terminus.common.data.transfer.api.service.dto.DataTransferTaskExecuteCallbackRequest;
import io.terminus.common.data.transfer.api.service.dto.TaskExecuteResult;
import io.terminus.common.data.transfer.executors.ifaces.IRegisteredTaskExecutor;
import io.terminus.common.data.transfer.spi.exceptions.DataImportInvalidRowException;
import io.terminus.common.data.transfer.spi.exceptions.DataTransferException;
import io.terminus.common.data.transfer.spi.ifaces.*;
import io.terminus.common.data.transfer.spi.models.BatchDataImportResult;
import io.terminus.common.data.transfer.spi.models.DataExportContext;
import io.terminus.common.data.transfer.spi.models.DataImportContext;
import io.terminus.common.data.transfer.spi.models.RowData;
import io.terminus.common.model.Paging;
import io.terminus.common.model.PagingCriteria;
import io.terminus.common.model.Response;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Integer.max;


/**
 * date 2018/9/26
 *
 * @author yushuo
 */

@Slf4j
@Service
public class RpcRegisteredTaskExecutor implements IRegisteredTaskExecutor {

    private static final int XLS_PAGE_SIZE = 10000;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final File tempDir;
    private final Map<String, IDataExporter> exporters = new ConcurrentHashMap<>();
    private final Map<String, IDataImporter> importers = new ConcurrentHashMap<>();

    static {
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new GuavaModule()); //注册guava类型
    }

    private final IFileManager fileManager;

    private final IDataFormatterFactory formatterFactory;

    private final ExecutorService executorService;

    private final IDataTransferTaskManageFacade taskManageFacade;

    private final ApplicationContext applicationContext;

    private final DubboProperties dubboProperties;

    @Autowired
    public RpcRegisteredTaskExecutor(
            IDataFormatterFactory formatterFactory,
            IFileManager fileManager,
            IDataTransferTaskManageFacade taskManageFacade,
            ApplicationContext applicationContext,
            DubboProperties dubboProperties
    ) throws IOException {
        this.fileManager = fileManager;
        this.formatterFactory = formatterFactory;
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("DataTransferExecutorThread-%d")
                .setDaemon(false).build();
        this.executorService = Executors.newFixedThreadPool(5, threadFactory);

        this.taskManageFacade = taskManageFacade;
        this.dubboProperties = dubboProperties;
        this.applicationContext = applicationContext;

        this.tempDir = Files.createTempDirectory("parana_exporter").toFile();
        this.registerShutdownHook();
    }

    @Override
    public void registerExporter(String name, IDataExporter dataExporter) {
        if(exporters.containsKey(name)) {
            throw new IllegalStateException(MessageFormat.format(
                    "Except only one exporter for name {0}, but found two.", name
            ));
        }
        exporters.put(name, dataExporter);
        registerToDubbo(name, DataTransferTaskType.EXPORT);
    }

    @Override
    public void registerImporter(String name, IDataImporter dataImporter) {
        if(importers.containsKey(name)) {
            throw new IllegalStateException(MessageFormat.format(
                    "Except only one exporter for name {0}, but found two.", name
            ));
        }
        importers.put(name, dataImporter);
        registerToDubbo(name, DataTransferTaskType.IMPORT);
    }

    private void registerToDubbo(String name, DataTransferTaskType type) {
        try {
            String groupName = type.name() + "__" + name;

            ServiceBean<IDataTransferTaskExecutor> serviceBean = new ServiceBean<>();
            serviceBean.setApplicationContext(applicationContext);
            serviceBean.setApplication(dubboProperties.getApplication());
            serviceBean.setProtocol(dubboProperties.getProtocol());
            serviceBean.setRegistry(dubboProperties.getRegistry());
            serviceBean.setProvider(dubboProperties.getProvider());
            if (dubboProperties.getProvider() != null) {
                serviceBean.setVersion(dubboProperties.getProvider().getVersion());
            }
            serviceBean.setInterface(IDataTransferTaskExecutor.class);
            serviceBean.setVersion(StringUtils.hasText(serviceBean.getVersion()) ? serviceBean.getVersion() : "1.0.0");
            serviceBean.setRetries(0);
            serviceBean.setGroup(groupName);

            serviceBean.setRef(this);
            serviceBean.afterPropertiesSet();
            serviceBean.export();

            log.info("[ DUBBO " + type.name() + " TASK EXECUTOR ] registered, taskName = {}", name);
        } catch (Exception e) {
            log.error("export service failed", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public Response<Boolean> executeTask(DataTransferTask task) {
        try {
            runTaskAsync(task);
            return Response.ok(Boolean.TRUE);
        } catch (Exception e) {
            log.error("execute export task error", e);
            return Response.fail(e.getMessage());
        }
    }

    private TaskExecuteResult runTask(DataTransferTask task) throws Exception {
        TaskExecuteResult taskExecuteResult;
        try {
            var taskType = DataTransferTaskType.fromCode(task.getType());
            if (taskType == DataTransferTaskType.EXPORT) {
                taskExecuteResult = doExport(task);
                log.info("DataExportTask {}:{} finished", task.getName(), task.getId());
            } else if (taskType == DataTransferTaskType.IMPORT) {
                taskExecuteResult = doImport(task);
                log.info("DataImportTask {}:{} finished", task.getName(), task.getId());
            } else {
                // This should never happen
                throw new IllegalStateException("unknown data transfer type");
            }
        } catch (DataTransferException dte) {
            log.error("[DataTransferTask] execute failed", dte);
            taskExecuteResult = new TaskExecuteResult(task);
            taskExecuteResult.setStatus(DataTransferTaskStatus.ERROR.getCode());
            taskExecuteResult.setMessage(dte.getMessage());
        } catch (Throwable t) {
            log.error("[DataTransferTask] execute failed", t);
            taskExecuteResult = new TaskExecuteResult(task);
            taskExecuteResult.setStatus(DataTransferTaskStatus.ERROR.getCode());
            taskExecuteResult.setMessage(t.getClass().getName() + ":" + t.getMessage());
        }
        taskManageFacade.taskExecuteCallback(new DataTransferTaskExecuteCallbackRequest(taskExecuteResult));
        return taskExecuteResult;
    }


    private Future<TaskExecuteResult> runTaskAsync(DataTransferTask task) {
        var taskType = DataTransferTaskType.fromCode(task.getType());
        if(null == taskType) {
            throw new IllegalArgumentException("data.transfer.task.type.invalid");
        }
        return executorService.submit(() -> {
            try {
                return runTask(task);
            } catch (Throwable t) {
                log.error("[DataTransferTask] async running task error", t);
            }
            return null;
        });
    }

    private IDataExporter getRegisteredExporter(String name) {
        IDataExporter exporter = exporters.get(name);
        if(null == exporter) {
            throw new IllegalArgumentException("exporter.not.exist");
        }
        return exporter;
    }

    private IDataImporter getRegisteredImporters(String name) {
        IDataImporter importer = importers.get(name);
        if(null == importer) {
            throw new IllegalArgumentException("importer.not.exist");
        }
        return importer;
    }

    @SuppressWarnings("unchecked")
    private TaskExecuteResult doExport(DataTransferTask task) throws Exception {
        IDataExporter dataExporter = getRegisteredExporter(task.getName());

        IDataFormatter dataFormatter = formatterFactory.formatter(
                dataExporter.modelClazz(),
                FileType.fromExtName(task.getFileExt())
        );
        dataFormatter.setHeaderAlias(dataExporter.headerAlias());

        PagingCriteria criteria = new PagingCriteria();
        if(!(dataExporter.criteriaClazz().isInstance(criteria))) {
            // criteriaClazz has been override
            // We do not recommend this. Export conditions preferred to be written in context.
            criteria = (PagingCriteria) objectMapper.readValue(task.getCriteriaJson(), dataExporter.criteriaClazz());
        }

        DataExportContext dataExportContext = (DataExportContext) dataExporter.contextClazz().newInstance();
        dataExportContext.setTask(task);
        dataExporter.exportStarted(criteria, dataExportContext);

        criteria.setPageNo(1);
        criteria.setPageSize(dataExporter.batchSize(dataExportContext));

        String exportFilePath = fileManager.generateFilePath(task);
        File tempFile = createTempFile(exportFilePath);
        AtomicLong exportedCount = new AtomicLong(0);

        try(OutputStream outputStream = new FileOutputStream(tempFile)) {
            while (criteria.hasNext()) {
                Paging resp = dataExporter.exportData(criteria, dataExportContext);
                var dataSet = resp.getData();
                if (CollectionUtils.isEmpty(dataSet)) {
                    break;
                } else {
                    dataSet.forEach(data -> {
                        if(null != data) {
                            exportedCount.getAndIncrement();
                            dataFormatter.writeLine(data, outputStream);
                        }
                    });
                    criteria.nextPage();
                }
            }
            dataFormatter.flush(outputStream);
        }
        fileManager.saveFile(exportFilePath, tempFile);
        deleteLocalFileSilently(tempFile);

        TaskExecuteResult taskResult = new TaskExecuteResult(task);
        taskResult.setStatus(DataTransferTaskStatus.SUCCESS.getCode());
        taskResult.setSuccessCount(exportedCount.get());
        taskResult.setFilePath(exportFilePath);

        try {
            dataExporter.exportFinished(taskResult, dataExportContext);
        } catch (Exception e) {
            taskResult.setStatus(DataTransferTaskStatus.FINISH_ERROR.getCode());
            taskResult.setMessage(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        return taskResult;
    }


    @SuppressWarnings("unchecked")
    private TaskExecuteResult doImport(DataTransferTask task) throws
            IOException, IllegalAccessException, InstantiationException, DataTransferException {
        IDataImporter importer = getRegisteredImporters(task.getName());
        AtomicLong successCount = new AtomicLong(0);

        Class modelClazz = importer.modelClazz();
        if((!BaseRowModel.class.isAssignableFrom(modelClazz)) && (!List.class.isAssignableFrom(modelClazz))) {
            throw new IllegalArgumentException("model class must be List<String> or BaseRowModel ");
        }
        ExcelHeadProperty excelHeadProperty =
                BaseRowModel.class.isAssignableFrom(modelClazz) ? new ExcelHeadProperty(modelClazz, new ArrayList<>()) : null;

        Class<? extends DataImportContext> contextClazz = importer.contextClazz();

        DataImportContext dataImportContext = contextClazz.newInstance();
        dataImportContext.setTask(task);

        List<Pair<RowData, String>> errorRows = new ArrayList<>();
        TaskExecuteResult taskResult = new TaskExecuteResult(task);

        try (InputStream inputStream = new BufferedInputStream(openStream(task.getFilePath()))) {
            ExcelReader excelReader = new ExcelReader(inputStream, task.getId(), new AnalysisEventListener<List<String>>() {

                List<List<String>> headLines = new ArrayList<>();
                Integer batchSize = ObjectUtils.defaultIfNull(importer.batchSize(), 0);
                List<RowData> batchRows = new ArrayList<>();

                @Override
                @SuppressWarnings("unchecked")
                public void invoke(List<String> originRowData, AnalysisContext analysisContext) {
                    if ((analysisContext.getCurrentRowNum() < importer.sheetProperty().getHeadLineMun())
                        && (analysisContext.getCurrentRowNum() <= importer.sheetProperty().getHeadLineMun() - 1)) {
                        headLines.add(originRowData);
                    } else {
                        RowData rowData = new RowData();
                        rowData.setCurrentRowNumber(analysisContext.getCurrentRowNum());
                        rowData.setOriginCellDatas(originRowData);
                        try {
                            rowData.setData(buildUserModel(analysisContext, originRowData));
                            doImport(rowData);
                        } catch (DataImportInvalidRowException ire) {
                            errorRows.add(Pair.of(rowData, ire.getMessage()));
                        } catch (Exception e) {
                            errorRows.add(Pair.of(rowData, e.getClass().getSimpleName() + ": " + e.getMessage()));
                        }
                    }
                }

                @SuppressWarnings("unchecked")
                private void doImport(RowData rowData) throws DataImportInvalidRowException {
                    if(batchSize > 0) {
                        batchRows.add(rowData);
                        if(batchRows.size() >= batchSize) {
                            doBatchImport();
                        }
                    } else {
                        importer.importRowData(rowData, dataImportContext);
                        successCount.incrementAndGet();
                    }
                }

                @SuppressWarnings("unchecked")
                private void doBatchImport() {
                    try {
                        BatchDataImportResult batchResult = importer.batchImportRowData(batchRows, dataImportContext);
                        if (batchResult != null) {
                            if (CollectionUtils.isNotEmpty(batchResult.getErrorRows())) {
                                errorRows.addAll(batchResult.getErrorRows());
                            }
                            successCount.addAndGet(batchResult.getSuccessRows().size());
                        } else {
                            // null result means all success
                            successCount.addAndGet(batchRows.size());
                        }
                    } catch (Exception e) {
                        log.error("[DataTransferTask] doBatchImportError", e);
                        String errorMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
                        batchRows.forEach(rowData ->  errorRows.add(Pair.of(rowData, errorMessage)));
                    }
                    batchRows = new ArrayList<>();
                }

                @Override
                @SuppressWarnings("unchecked")
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                    try {
                        if(CollectionUtils.isNotEmpty(batchRows)) {
                            doBatchImport();
                        }
                        taskResult.setSuccessCount(successCount.get());
                        taskResult.setErrorCount((long) errorRows.size());
                        taskResult.setStatus(DataTransferTaskStatus.SUCCESS.getCode());

                        if (CollectionUtils.isNotEmpty(errorRows)) {
                            String importFailRecordsPath = fileManager.generateFilePath(task);
                            saveFailRecordsToFile(importFailRecordsPath, headLines, errorRows, excelHeadProperty);
                            taskResult.setErrorRecordsFilePath(importFailRecordsPath);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        taskResult.setStatus(DataTransferTaskStatus.ERROR.getCode());
                        taskResult.setMessage(e.getClass().getSimpleName() + ": " + e.getMessage());
                    }
                    try {
                        importer.importFinished(taskResult, dataImportContext);
                    } catch (Exception e) {
                        taskResult.setStatus(DataTransferTaskStatus.FINISH_ERROR.getCode());
                        taskResult.setMessage(e.getClass().getSimpleName() + ": " + e.getMessage());
                    }
                }

                private Object buildUserModel(AnalysisContext context, List<String> stringList) throws Exception {
                    if(null != excelHeadProperty) {
                        Object resultModel = modelClazz.newInstance();
                        BeanMap.create(resultModel).putAll(
                                TypeUtil.getFieldValues(stringList, excelHeadProperty, context.use1904WindowDate()));
                        return resultModel;
                    } else {
                        return stringList;
                    }
                }

            }, false);

            Sheet modelLessSheet = new Sheet(importer.sheetProperty().getSheetNo(), 0);
            importer.importStarted(dataImportContext);
            excelReader.read(modelLessSheet);
            return taskResult;
        }
    }

    private void saveFailRecordsToFile(
            String importFailRecordsPath, List<List<String>> headLines, List<Pair<RowData, String>> errorRows, ExcelHeadProperty excelHeadProperty)
            throws IOException {
        File tmpFile = createTempFile(importFailRecordsPath);

        try (SXSSFWorkbook xlsBook = new SXSSFWorkbook(XLS_PAGE_SIZE)) {
            org.apache.poi.ss.usermodel.Sheet xlsBookSheet = xlsBook.createSheet();
            try(OutputStream outputStream = new FileOutputStream(tmpFile)) {
                int rowNum = 0;
                int errColNum = 0;

                if(CollectionUtils.isNotEmpty(headLines)) {
                    errColNum = headLines.stream().mapToInt(List::size).max().orElse(0);
                    for(List<String> headLine : headLines) {
                        Row row = xlsBookSheet.createRow(rowNum++);
                        int colNum = 0;
                        for( ; colNum < headLine.size(); ++colNum) {
                            WorkBookUtil.createCell(row, colNum, null, headLine.get(colNum));
                        }
                        WorkBookUtil.createCell(row, colNum, null, "ERROR");
                    }
                }

                for (var errorRow : errorRows) {
                    Row row = xlsBookSheet.createRow(rowNum++);
                    //noinspection unchecked
                    List<String> errorRowData = errorRow.getLeft().getOriginCellDatas();
                    String errMsg = errorRow.getRight();

                    int colNum = 0;
                    for (String cellValue : errorRowData) {
                        Cell cell = WorkBookUtil.createCell(row, colNum, null, cellValue);
                        if(null != excelHeadProperty) {
                            ExcelColumnProperty columnProperty = excelHeadProperty.getExcelColumnProperty(colNum);
                            if (null != columnProperty && Date.class.isAssignableFrom(columnProperty.getField().getType())) {
                                try {
                                    String cellValueFormatted = TypeUtil.formatDate(DateUtil.getJavaDate(
                                            Double.parseDouble(cellValue)), columnProperty.getFormat());
                                    cell.setCellValue(cellValueFormatted);
                                } catch (Exception e) {
                                    // skip
                                }
                            }
                        }
                        ++colNum;
                    }
                    WorkBookUtil.createCell(row, max(colNum, errColNum), null, errMsg);
                }
                xlsBook.write(outputStream);
            }
        }

        fileManager.saveFile(importFailRecordsPath, tmpFile);
        deleteLocalFileSilently(tmpFile);
    }

    private InputStream openStream(String filePath) throws IOException {
        try {
            return fileManager.openStream(filePath);
        } catch (Exception e) {
            throw new IOException(MessageFormat.format("fail to connection url, url:({0})", filePath), e);
        }
    }

    private void deleteLocalFileSilently(File file) {
        try {
            Files.delete(file.toPath());
        } catch (Exception fe) {
            log.error("file delete error", fe);
        }
    }

    private File createTempFile(String filePath) throws IOException {

        String tempFileName = UUID.randomUUID().toString() + "__" + Paths.get(filePath).getFileName().toString();

        String absFilePath = Paths.get(tempDir.getPath(), tempFileName).toString();
        File directory = new File(Paths.get(absFilePath).getParent().toString());
        if(!directory.exists()){
            boolean d = directory.mkdirs();
            if(!d) {
                throw new IOException(MessageFormat.format(
                        "Failed to create folder, {0}", directory));
            }
        }
        return new File(absFilePath);
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                FileUtils.deleteDirectory(tempDir);
            } catch (IOException e) {
                // logger may have already shutdown
                e.printStackTrace();
            }
        }));
    }

}
