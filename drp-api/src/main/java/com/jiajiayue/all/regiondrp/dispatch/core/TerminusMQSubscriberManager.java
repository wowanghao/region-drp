package com.jiajiayue.all.regiondrp.dispatch.core;

import io.terminus.common.rocketmq.annotation.ConsumeMode;
import io.terminus.common.rocketmq.annotation.MQConsumer;
import io.terminus.common.rocketmq.annotation.MQSubscribe;
import io.terminus.common.rocketmq.properties.RocketMQProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * @author WangHao
 * @date 2019/5/20 19:53
 */

public class TerminusMQSubscriberManager implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(io.terminus.common.rocketmq.core.TerminusMQSubscriberManager.class);
    private final RocketMQProperties properties;

    public TerminusMQSubscriberManager(RocketMQProperties properties) {
        this.properties = properties;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> consumers = applicationContext.getBeansWithAnnotation(MQConsumer.class);
        Iterator var3 = consumers.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry)var3.next();
            Object bean = entry.getValue();
            MQConsumer consumer = (MQConsumer)bean.getClass().getAnnotation(MQConsumer.class);
            Method[] methods = bean.getClass().getDeclaredMethods();
            Method[] var8 = methods;
            int var9 = methods.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                Method method = var8[var10];
                MQSubscribe subscribe = (MQSubscribe)method.getAnnotation(MQSubscribe.class);
                if (subscribe != null) {
                    try {
                        Type[] parameterTypes = method.getGenericParameterTypes();
                        if (ArrayUtils.isEmpty(parameterTypes)) {
                            logger.warn("subscribe method parameter is empty.");
                            return;
                        }

                        Type type = parameterTypes[0];
                        if (type instanceof Class) {
                            String consumerGroup = (String)StringUtils.defaultIfEmpty(consumer.consumerGroup(), this.properties.getProducerGroup());
                            DefaultMQPushConsumer defaultConsumer = new DefaultMQPushConsumer(consumerGroup);
                            defaultConsumer.setNamesrvAddr(this.properties.getNameServerAddress());
                            defaultConsumer.setMessageModel(subscribe.messageMode());
                            defaultConsumer.subscribe(subscribe.topic(), StringUtils.join(subscribe.tag(), "||"));
                            defaultConsumer.setInstanceName(UUID.randomUUID().toString());
                            Object listener;
                            if (ConsumeMode.CONCURRENTLY.equals(subscribe.consumeMode())) {
                                listener = new ConcurrentlyMessageListener((Class)type, bean, method);
                            } else {
                                listener = new OrderlyMessageListener((Class)type, bean, method);
                            }

                            defaultConsumer.registerMessageListener((MessageListener)listener);
                            defaultConsumer.start();
                        }
                    } catch (MQClientException var18) {
                        var18.printStackTrace();
                    }
                }
            }
        }

    }
}
