package com.jiajiayue.all.regiondrp.dispatch.listener;

import io.jjy.platform.common.constant.TopicEnum;
import io.jjy.platform.common.event.BaseExecutionEvent;
import io.jjy.platform.common.event.DispatchClientExecutionEvent;
import io.jjy.platform.common.event.DrpEvent;
import io.jjy.platform.common.util.ExeceptionUtil;
import io.terminus.common.rocketmq.core.TerminusMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

/**
 * @program: interface-platform
 * @description:
 * @author: Chenguojian  guojian.cgj@alibaba-inc.com
 * @create: 2018-11-27 09:34
 **/
@Slf4j
public abstract class AbstractListener {

    @Autowired
    private TerminusMQProducer mqProducer;

    /**
     *  成功方法
     * @param object
     */
    protected void success(Object object) {
        if (Objects.nonNull(object)){
            if (object instanceof  DrpEvent){
                DrpEvent drpEvent = (DrpEvent)object;
                DispatchClientExecutionEvent event = buildBase(drpEvent);
                event.setExecutionMethod("GET");
                event.setExecutionState(200);
                event.setMessage("execute success");
                send(TopicEnum.CLIENT_EXECUTE_LOG_TOPIC,event);
            }
        }else {
            throw new RuntimeException();
        }
    }

    /**
     * 失败方法
     * @param object
     * @param e
     */
    protected void fail(Object object,Exception e) {
        if (Objects.nonNull(object)){
            if (object instanceof DrpEvent) {
                DrpEvent drpEvent = (DrpEvent)object;
                DispatchClientExecutionEvent event = buildBase(drpEvent);
                //具体方法可能需要自己是实现
                event.setExecutionMethod("GET");
                event.setExecutionState(500);
                event.setMessage(ExeceptionUtil.getErrorInfoFromException(e));
                send(TopicEnum.CLIENT_EXECUTE_LOG_TOPIC,event);
            }
        }
    }

    private DispatchClientExecutionEvent  buildBase(DrpEvent drpEvent){
        return DispatchClientExecutionEvent.builder().executionParams(drpEvent.getDataJson())
                .executionUrl("???").source(drpEvent.getBizType()).build();
    }

    /**
     *
     * @param topic
     * @param event
     */
    private void send(String topic , BaseExecutionEvent event){

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

    /**
     *  订阅消息
     * @param drpEvent
     */
    protected abstract void subscribe(DrpEvent drpEvent);

}
