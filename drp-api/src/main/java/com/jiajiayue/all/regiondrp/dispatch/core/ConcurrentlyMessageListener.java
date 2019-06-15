package com.jiajiayue.all.regiondrp.dispatch.core;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * @author WangHao
 * @date 2019/5/20 19:55
 */

public class ConcurrentlyMessageListener extends AbstractMessageListener implements MessageListenerConcurrently {
    ConcurrentlyMessageListener(Class messageType, Object bean, Method method) {
        super(messageType, bean, method);
    }

    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageList, ConsumeConcurrentlyContext context) {
        Iterator var3 = messageList.iterator();

        MessageExt messageExt;
        do {
            if (!var3.hasNext()) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            messageExt = (MessageExt)var3.next();
        } while(this.process(messageExt));

        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }
}
