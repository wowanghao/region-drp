package io.terminus.common.data.transfer.spi.models;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RowData<T> implements Serializable {

    Integer currentRowNumber;

    List<String> originCellDatas;

    T data;

}
