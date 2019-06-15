package com.jiajiayue.all.regiondrp.dispatch.listener;


import com.alibaba.fastjson.JSON;
import com.jiajiayue.all.regiondrp.biz.model.MktStockInit;
import com.jiajiayue.all.regiondrp.biz.service.MktStockWriteService;
import com.jiajiayue.all.regiondrp.common.util.HttpParametersUtils;
import com.jiajiayue.all.regiondrp.dispatch.manager.MQManager;
import io.jjy.platform.common.event.DispatchClientExecutionEvent;
import io.jjy.platform.common.event.DrpEvent;
import io.jjy.platform.common.event.OpenPlatformExecutionEvent;
import io.terminus.common.rocketmq.annotation.ConsumeMode;
import io.terminus.common.rocketmq.annotation.MQConsumer;
import io.terminus.common.rocketmq.annotation.MQSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
