package io.terminus.common.data.transfer.api.service.dto;

import io.terminus.api.consts.OperationType;
import io.terminus.common.data.transfer.api.model.DataTransferTaskOperationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yushuo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataTransferTaskQueryRequest extends DataTransferManageRequest {

    private Long taskId;

    @Override
    public OperationType getOperationType() {
        return DataTransferTaskOperationType.DATA_TRANSFER_TASK_QUERY;
    }

}

