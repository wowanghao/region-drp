package com.jiajiayue.all.regiondrp.dispatch.core;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * @author WangHao
 * @date 2019/5/20 19:59
 */

public class OrderlyMessageListener extends AbstractMessageListener implements MessageListenerOrderly {
    OrderlyMessageListener(Class messageType, Object bean, Method method) {
        super(messageType, bean, method);
    }

    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messageList, ConsumeOrderlyContext context) {
        Iterator var3 = messageList.iterator();

        MessageExt messageExt;
        do {
            if (!var3.hasNext()) {
                return ConsumeOrderlyStatus.SUCCESS;
            }

            messageExt = (MessageExt)var3.next();
        } while(this.process(messageExt));

        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
    }
}
