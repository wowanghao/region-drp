package com.jiajiayue.all.regiondrp.common.request;

import io.terminus.common.model.Criteria;
import lombok.Data;

/**
 * autho: wanghao
 */
public abstract class AbstractRequest extends Criteria {
    /**
     * 检查请求参数
     * 报错抛异常
     */
    public abstract void checkParam();
}