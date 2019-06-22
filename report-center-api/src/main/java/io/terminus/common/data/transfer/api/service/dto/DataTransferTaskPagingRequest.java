package io.terminus.common.data.transfer.api.service.dto;

import io.terminus.api.consts.OperationType;
import io.terminus.common.data.transfer.api.model.DataTransferTaskType;
import io.terminus.common.data.transfer.api.model.DataTransferTaskOperationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author yushuo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataTransferTaskPagingRequest extends DataTransferManageRequest {

    private Integer pageNo = 1;

    private Integer pageSize = 20;

    /**
     * 任务名称，整个大项目全局唯一，与代码中的Exporter/Importer对应
     */
    private String name;

    /**
     * 任务展示名称
     */
    private String displayName;

    /**
     * 任务类型: import/export
     * @see DataTransferTaskType
     */
    private Integer taskType;

    private Date createTimeStart;

    private Date createTimeEnd;

    private Integer frontStatus;


    @Override
    public OperationType getOperationType() {
        return DataTransferTaskOperationType.DATA_TRANSFER_TASK_QUERY;
    }

}
