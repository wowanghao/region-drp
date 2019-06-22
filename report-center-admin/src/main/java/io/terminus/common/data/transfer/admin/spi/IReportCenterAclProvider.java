package io.terminus.common.data.transfer.admin.spi;

/**
 * @author yushuo
 */
public interface IReportCenterAclProvider {

    boolean userIsAdmin(Long userId);

}
