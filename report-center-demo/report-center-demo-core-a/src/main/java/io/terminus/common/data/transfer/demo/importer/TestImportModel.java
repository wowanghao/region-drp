package io.terminus.common.data.transfer.demo.importer;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * AUTHOR: zhangbin
 * ON: 2018/11/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TestImportModel extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String skuCode;

    @ExcelProperty(index = 1)
    private String warehouseCode;

    @ExcelProperty(index = 2)
    private Long productType;

    @ExcelProperty(index = 3)
    private Date date;
}
