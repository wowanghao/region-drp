/*
package com.jiajiayue.all.regiondrp.schedule.job;

import io.jjy.platform.common.event.DrpScheduleExecutionEvent;
import io.terminus.common.rocketmq.core.TerminusMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

*/
/**
 * @author WangHao
 * @date 2019/5/26 2:10
 *//*


public class AbstractJob {
    private static final Logger log = LoggerFactory.getLogger(AbstractJob.class);
    @Autowired
    protected TerminusMQProducer mqProducer;

    public AbstractJob() {
    }

    public void sendLog(String topic, DrpScheduleExecutionEvent drpScheduleExecutionEvent) {
        try {
            this.mqProducer.send(topic, drpScheduleExecutionEvent);
        } catch (Throwable var4) {
            log.error("send message failed , message: " + drpScheduleExecutionEvent);
        }

    }
}

*/
