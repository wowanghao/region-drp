package io.terminus.common.data.transfer.demo.importer;

import com.alibaba.excel.metadata.Sheet;
import io.terminus.common.data.transfer.api.service.dto.TaskExecuteResult;
import io.terminus.common.data.transfer.spi.annotations.DataImporter;
import io.terminus.common.data.transfer.spi.exceptions.DataImportInvalidRowException;
import io.terminus.common.data.transfer.spi.ifaces.IDataImporter;
import io.terminus.common.data.transfer.spi.models.DataImportContext;
import io.terminus.common.data.transfer.spi.models.RowData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * AUTHOR: zhangbin
 * ON: 2018/11/26
 */
@Slf4j
@DataImporter(name = "list")
public class TestListImporter implements IDataImporter {

    @Override
    public Sheet sheetProperty() {
        return new Sheet(/*第一个sheet*/1, /*标题1行*/1);
    }

    @Override
    public void importRowData(RowData data, DataImportContext context) throws DataImportInvalidRowException {
        Object model = data.getData();
        log.info("row {} -- {}", data.getCurrentRowNumber(), model);
    }

    public void importFinished(TaskExecuteResult result, MyTestImportContext context) {
        log.info("ALL DONE: stat = {}", result);
        log.info("MyField:{}", context.getMyField());
        result.setExtraJson(context.getMyField());
    }

}
