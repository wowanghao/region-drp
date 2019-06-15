package com.jiajiayue.all.regiondrp.dispatch.core;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.terminus.common.rocketmq.core.TerminusMQSubscriberManager;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author WangHao
 * @date 2019/5/20 19:56
 */

abstract class AbstractMessageListener {
    private static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(TerminusMQSubscriberManager.class);
    private final Class messageType;
    private final Object bean;
    private final Method method;

    AbstractMessageListener(Class messageType, Object bean, Method method) {
        this.messageType = messageType;
        this.bean = bean;
        this.method = method;
    }

    private Object parseMessage(MessageExt msg) {
        if (msg != null && msg.getBody() != null) {
            try {
                return gson.fromJson(new String(msg.getBody()), this.messageType);
            } catch (JsonSyntaxException var3) {
                logger.error("parse message json fail : {}", var3.getMessage());
                return null;
            }
        } else {
            logger.warn("message is empty.");
            return null;
        }
    }

    boolean process(MessageExt msg) {
        try {
            Object body = this.parseMessage(msg);
            this.method.invoke(this.bean, body);
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            logger.warn("consume fail , ask for re-consume , msgId: {}", msg.getMsgId());
            return false;
        }
    }
}
