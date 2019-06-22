package io.terminus.common.data.transfer.spi.models;

import io.terminus.common.data.transfer.api.model.DataTransferTask;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yushuo
 */
@Data
@NoArgsConstructor
public class DataTransferContext {

    private DataTransferTask task;

}
