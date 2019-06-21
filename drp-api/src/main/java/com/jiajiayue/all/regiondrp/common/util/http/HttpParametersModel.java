package com.jiajiayue.all.regiondrp.common.util.http;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangHao
 * @date 2019/6/19 10:49
 */
@Data
@Builder
public class HttpParametersModel {
    private String requestUrl;
    @Builder.Default
    private Map paraMap = new HashMap();
    private Object bodyObj;
    @Builder.Default
    private Integer timeout = 60000;
    @Builder.Default
    private Map<String, String> headerMap = new HashMap();
}
