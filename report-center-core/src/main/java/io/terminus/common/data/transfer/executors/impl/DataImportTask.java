package io.terminus.common.data.transfer.executors.impl;

import io.terminus.common.data.transfer.api.model.DataTransferTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * AUTHOR: zhangbin
 * ON: 2018/11/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class DataImportTask implements Serializable {
    private static final long serialVersionUID = -7110138859868908610L;

    private Long id;

    private String name;

    private String desc;

    private String filePath;

    private String fileExt;

    private DataTransferTaskStatus status;

    /**
     * 出错时的错误信息
     */
    private String message;

    /**
     * 完成时间
     */
    private Date finishedAt;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}
