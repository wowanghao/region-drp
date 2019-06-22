package io.terminus.common.data.transfer.api.service.dto;

import io.terminus.api.request.AbstractRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yushuo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class DataTransferTaskRequest extends AbstractRequest {

    private Long taskId;

}
