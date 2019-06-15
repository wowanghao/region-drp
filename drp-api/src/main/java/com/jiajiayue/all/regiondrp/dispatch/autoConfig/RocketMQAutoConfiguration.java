package com.jiajiayue.all.regiondrp.dispatch.autoConfig;

import io.terminus.common.rocketmq.core.TerminusMQProducer;
import io.terminus.common.rocketmq.core.TerminusMQSubscriberManager;
import io.terminus.common.rocketmq.properties.RocketMQProperties;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author WangHao
 * @date 2019/5/20 19:48
 */

@Primary
@Configuration
@EnableConfigurationProperties({RocketMQProperties.class})
public class RocketMQAutoConfiguration implements ApplicationContextAware {
    private ConfigurableApplicationContext applicationContext;
    @Autowired
    private RocketMQProperties properties;

    public RocketMQAutoConfiguration() {
    }

    @Bean
    public TerminusMQSubscriberManager terminusMQSubscriberManager() {
        return new TerminusMQSubscriberManager(this.properties);
    }

    @Bean
    public TerminusMQProducer terminusMQProducer() throws MQClientException {
        return new TerminusMQProducer(this.properties);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
