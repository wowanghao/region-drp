package com.jiajiayue.all.regiondrp.dispatch.manager;

import io.jjy.platform.common.event.BaseExecutionEvent;
import io.terminus.common.rocketmq.core.TerminusMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: interface-platform
 * @description:
 * @author: Chenguojian  guojian.cgj@alibaba-inc.com
 * @create: 2018-11-26 10:06
 **/
@Component
@Slf4j
public class MQManager {

    @Autowired
    private TerminusMQProducer mqProducer;

    public void send(String topic , BaseExecutionEvent event){

        event.setExecutionAt(new Date(System.currentTimeMillis()));

        mqProducer.asyncSend(topic, event, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("execute success");
            }

            @Override
            public void onException(Throwable e) {
                log.error("execute success",e,event);
            }
        });
    }

}

