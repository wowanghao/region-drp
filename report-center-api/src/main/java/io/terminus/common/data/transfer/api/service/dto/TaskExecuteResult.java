package io.terminus.common.data.transfer.api.service.dto;

import io.terminus.common.data.transfer.api.model.DataTransferTask;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yushuo
 */

@Data
public class TaskExecuteResult implements Serializable {

    private Long taskId;

    /**
     * 成功数量
     */
    private Long successCount;

    /**
     * 失败数量
     */
    private Long errorCount;

    /**
     * 文件下载地址
     */
    private String filePath;

    /**
     * 当前状态
     * @see io.terminus.common.data.transfer.api.model.DataTransferTaskStatus
     */
    private Integer status;

    /**
     * 错误信息提示
     */
    private String message;

    /**
     * 额外信息
     */
    private String extraJson;

    /**
     * 失败行文件下载地址
     */
    private String errorRecordsFilePath;

    public TaskExecuteResult() {}

    public TaskExecuteResult(DataTransferTask task) {
        this.taskId = task.getId();
    }

    public TaskExecuteResult(Long taskId) {
        this.taskId = taskId;
    }

}
