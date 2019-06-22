package io.terminus.common.data.transfer.api.service.dto;

import io.terminus.api.request.AbstractRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yushuo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class DataTransferManageRequest extends AbstractRequest {

    private Long userId;

    private String userName;

    private String userNickname;

    private boolean isAdmin = false;

}
