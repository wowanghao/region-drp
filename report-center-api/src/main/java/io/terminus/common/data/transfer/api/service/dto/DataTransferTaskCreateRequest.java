package io.terminus.common.data.transfer.api.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import io.terminus.api.consts.OperationType;
import io.terminus.api.utils.ParamUtil;
import io.terminus.common.data.transfer.api.exceptions.DataTransferErrorCode;
import io.terminus.common.data.transfer.api.model.DataTransferTaskType;
import io.terminus.common.data.transfer.api.model.FileType;
import io.terminus.common.data.transfer.api.model.DataTransferTaskOperationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yushuo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataTransferTaskCreateRequest extends DataTransferManageRequest {

    /**
     * 任务名称，整个大项目全局唯一，与代码中的Exporter/Importer对应
     */
    private String name;

    /**
     * 任务的展示名称
     */
    private String displayName;

    /**
     * 任务描述
     */
    private String desc;

    /**
     * 任务执行的条件，会作为任务的执行参数传给具体的importer/exporter
     */
    @JsonProperty("criteria")
    @JsonRawValue
    private String criteriaJson;

    /**
     * 任务类型: import/export
     * @see DataTransferTaskType
     */
    private Integer taskType;

    /**
     * 文件地址, 导入任务需要填
     */
    private String filePath;

    /**
     * 文件类型, 当前只支持xlsx.
     */
    private String fileExt;

    @Override
    public OperationType getOperationType() {
        if(DataTransferTaskType.EXPORT.getCode() == this.getTaskType()) {
            return DataTransferTaskOperationType.DATA_EXPORT_TASK_CREATE;
        } else if (DataTransferTaskType.IMPORT.getCode() == this.getTaskType()) {
            return DataTransferTaskOperationType.DATA_IMPORT_TASK_CREATE;
        }
        return null;
    }

    @Override
    public void checkParam() {
        ParamUtil.notBlank(this.getName(), DataTransferErrorCode.TASK_NAME_INVALID.getCode());
        ParamUtil.nonNull(this.getTaskType(), DataTransferErrorCode.TASK_TYPE_INVALID.getCode());
        if (DataTransferTaskType.IMPORT.getCode() == this.getTaskType()) {
            ParamUtil.notBlank(this.getFilePath(), DataTransferErrorCode.IMPORT_TASK_FILE_INVALID.getCode());
        } else if(DataTransferTaskType.EXPORT.getCode() == this.getTaskType()) {
            ParamUtil.nonNull(FileType.fromExtName(fileExt), DataTransferErrorCode.FILE_EXT_NAME_INVALID.getCode());
        }
        super.checkParam();
    }

}
