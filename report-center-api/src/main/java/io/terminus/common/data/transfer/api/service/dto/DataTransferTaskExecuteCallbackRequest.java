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
public class DataTransferTaskExecuteCallbackRequest extends DataTransferTaskRequest {

    private TaskExecuteResult taskExecuteResult;

    public DataTransferTaskExecuteCallbackRequest() {}

    public DataTransferTaskExecuteCallbackRequest(TaskExecuteResult taskExecuteResult) {
        this.setTaskId(taskExecuteResult.getTaskId());
        this.taskExecuteResult = taskExecuteResult;
    }

    @Override
    public OperationType getOperationType() {
        return DataTransferTaskOperationType.DATA_TRANSFER_TASK_EXECUTE;
    }

}

