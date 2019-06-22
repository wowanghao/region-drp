package io.terminus.common.data.transfer.spi.models;

import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yushuo
 */
@Data
@AllArgsConstructor
public class DataImportErrorRow<T extends BaseRowModel> {

    private T data;

    private String errorMessage;

    public static <T extends BaseRowModel> DataImportErrorRow<T> of(T data, String errorMessage) {
        return new DataImportErrorRow<>(data, errorMessage);
    }

}
