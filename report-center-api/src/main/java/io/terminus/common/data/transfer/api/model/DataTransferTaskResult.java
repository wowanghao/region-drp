package io.terminus.common.data.transfer.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yushuo
 */

@Data
public class DataTransferTaskResult implements Serializable {

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * @see DataTransferTaskStatus
     */
    private Integer status;

    /**
     * 出错时的错误信息
     */
    private String message;

    /**
     * 额外信息
     */
    @JsonProperty("extra")
    @JsonRawValue
    private String extraJson;

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
    private String fileUrl;

    /**
     * 失败行文件下载地址
     */
    private String errorRecordsFileUrl;

}
