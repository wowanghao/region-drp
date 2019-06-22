package io.terminus.common.data.transfer.executors.impl;

import io.terminus.common.data.transfer.api.model.DataTransferTaskStatus;
import io.terminus.common.model.PagingCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DataExportTask implements Serializable {

    private Long id;

    /**
     * The name of exporter
     * indicates which exporter to use
     */
    private String name;

    /**
     * Description of task
     */
    private String desc;

    /**
     * criteria will passed to exporter
     */
    private PagingCriteria criteria;

    private String filePath;

    private String fileExt;

    private DataTransferTaskStatus status;

    private String message;

    private Date createdAt;

    private Date updatedAt;

    private Date finishedAt;

}
