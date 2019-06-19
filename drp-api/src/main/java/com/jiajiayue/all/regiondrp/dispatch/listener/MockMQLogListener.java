package com.jiajiayue.all.regiondrp.dispatch.listener;


import io.jjy.platform.common.event.OpenPlatformExecutionEvent;
import io.terminus.common.rocketmq.annotation.ConsumeMode;
import io.terminus.common.rocketmq.annotation.MQConsumer;
import io.terminus.common.rocketmq.annotation.MQSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: wh
 * @create: 2019-05-21 13:58
 **/
@Slf4j
@Component
@MQConsumer(consumerGroup = "drp_schedule1")
public class MockMQLogListener{


    @MQSubscribe(topic = "server_execute_log", consumeMode = ConsumeMode.ORDERLY)
    public void subscribe(OpenPlatformExecutionEvent openPlatformExecutionEvent) {
        log.info(openPlatformExecutionEvent.toString());
    }



}
