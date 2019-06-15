package com.jiajiayue.all.regiondrp.constant;

import io.jjy.platform.common.constant.TopicEnum;
import io.jjy.platform.common.enums.DrpBusinessType;
import lombok.Getter;

/**
 * @author huxiaohui
 * @date 2018/11/16 15:05
 */
public enum BusinessType implements DrpBusinessType {

    STORE("store", "drp_job_event_topic_store", "库存"),
    ITEM("item", TopicEnum.DRP_JOB_EVENT_TOPIC_ITEM, "商品"),
    ORDER("order", TopicEnum.DRP_JOB_EVENT_TOPIC_ORDER, "订单");

    @Getter
    private String val;
    @Getter
    private String topic;
    @Getter
    private String des;

    BusinessType(String val, String topic, String des){
        this.val = val;
        this.topic = topic;
        this.des = des;
    }

}
