package io.terminus.common.data.transfer.demo.importer;

import com.alibaba.excel.metadata.Sheet;
import io.terminus.common.data.transfer.api.service.dto.TaskExecuteResult;
import io.terminus.common.data.transfer.spi.annotations.DataImporter;
import io.terminus.common.data.transfer.spi.exceptions.DataImportInvalidRowException;
import io.terminus.common.data.transfer.spi.ifaces.IDataImporter;
import io.terminus.common.data.transfer.spi.models.BatchDataImportResult;
import io.terminus.common.data.transfer.spi.models.RowData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * AUTHOR: zhangbin
 * ON: 2018/11/26
 */
@Slf4j
@DataImporter(name = "test")
public class TestImporter implements IDataImporter<TestImportModel, MyTestImportContext> {

    @Override
    public Sheet sheetProperty() {
        return new Sheet(/*第一个sheet*/1, /*标题1行*/1,/*结果对象*/ TestImportModel.class);
    }

    @Override
    public Integer batchSize() {
        // 这里返回null, 则使用单行导入，只会调用importRowData
        // 如果返回大于0， 则只会调用batchImportRowData
        // 强烈建议这里用单行导入。使用batchImport，需要精确控制每行的错误逻辑
        return 3;
    }

    @Override
    public void importStarted(MyTestImportContext context) {
        context.setMyField("HA!");
    }

    @Override
    public void importRowData(RowData<TestImportModel> rowData, MyTestImportContext context) throws DataImportInvalidRowException {
        TestImportModel model = rowData.getData();
        log.info("row {} -- {}", rowData.getCurrentRowNumber(), model);

        if(model.getProductType() > 2) {
            throw new DataImportInvalidRowException("sampleError");
        }
    }

    @Override
    public BatchDataImportResult<TestImportModel> batchImportRowData(List<RowData<TestImportModel>> batchData, MyTestImportContext context) {
        BatchDataImportResult<TestImportModel> result = new BatchDataImportResult<>();
        batchData.forEach(rowData -> {
            TestImportModel model = rowData.getData();
            log.info("row {} -- {}", rowData.getCurrentRowNumber(), model);

            if(null == model.getProductType() || model.getProductType() > 2) {
                result.addErrorRow(rowData, "示例错误");
            } else {
                result.addSuccessRow(rowData);
            }
        });
        context.setMyField(context.getMyField() + "HO!");
        return result;
    }

    @Override
    public void importFinished(TaskExecuteResult result, MyTestImportContext context) {
        log.info("ALL DONE: stat = {}", result);
        log.info("MyField:{}", context.getMyField());
        result.setExtraJson(context.getMyField());
    }

}
