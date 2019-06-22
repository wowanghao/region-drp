package io.terminus.common.data.transfer;

import io.terminus.boot.rpc.dubbo.light.common.DubboProperties;
import io.terminus.boot.rpc.dubbo.light.consumer.ServiceSubscriber;
import io.terminus.common.data.transfer.api.facade.IDataTransferTaskManageFacade;
import io.terminus.common.data.transfer.configuration.OssDataTransferConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(OssDataTransferConfiguration.class)
@EnableConfigurationProperties({DubboProperties.class})
@ComponentScan
public class DataTransferCoreAutoConfiguration {

    private final ServiceSubscriber serviceSubscriber;

    @Autowired
    public DataTransferCoreAutoConfiguration(ServiceSubscriber serviceSubscriber) {
        this.serviceSubscriber = serviceSubscriber;
    }

    @Bean
    @ConditionalOnMissingBean
    IDataTransferTaskManageFacade dataTransferTaskManageFacade() {
        return serviceSubscriber.consumer(IDataTransferTaskManageFacade.class).timeout(3000).subscribe();
    }
}
