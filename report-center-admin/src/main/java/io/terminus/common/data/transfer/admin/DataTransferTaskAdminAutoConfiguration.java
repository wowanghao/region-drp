package io.terminus.common.data.transfer.admin;

import io.terminus.boot.rpc.dubbo.light.consumer.ServiceSubscriber;
import io.terminus.common.data.transfer.admin.spi.IReportCenterAclProvider;
import io.terminus.common.data.transfer.api.facade.IDataTransferTaskManageFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yushuo
 */
@Slf4j
@Configuration
@ComponentScan
public class DataTransferTaskAdminAutoConfiguration {

    private final ServiceSubscriber serviceSubscriber;

    @Autowired
    public DataTransferTaskAdminAutoConfiguration(ServiceSubscriber serviceSubscriber) {
        this.serviceSubscriber = serviceSubscriber;
    }

    @Bean
    @ConditionalOnMissingBean
    IDataTransferTaskManageFacade dataTransferTaskManageFacade() {
        return serviceSubscriber.consumer(IDataTransferTaskManageFacade.class).timeout(3000).subscribe();
    }

    @Bean
    @ConditionalOnMissingBean
    IReportCenterAclProvider reportCenterAclProvider() {
        return userId -> false;
    }
}
