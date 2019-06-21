package com.jiajiayue.all.regiondrp.common.util.http;


import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.function.Function;

/**
 * @author zhangxiang@terminus.io
 * @date 2019/1/3
 */
public class Invoker {

    /**
     * facade try catch
     *
     * @param param    request
     * @param function param -> { .. }
     * @return Response.ok(response) or Response.fail("error.code")
     */
    public static HttpResponseModel andResponse(String param, Function<Map, Object> function,
                                                String successKey, Object successVal) {
        try {
            Map responseBodyMap = JSONObject.parseObject(param, Map.class);
            if (successVal instanceof Boolean) {
                if (!(Boolean) responseBodyMap.get(successKey)) {
                    throw new Exception(param);
                }
            }
            if (successVal instanceof String) {
                if (!responseBodyMap.get(successKey).equals(successVal)) {
                    throw new Exception(param);
                }
            }
            return HttpResponseModel.ok(function.apply(responseBodyMap));
        } catch (Throwable e) {
            // return logAndResponseFail(e, param, function);
            e.printStackTrace();
            return HttpResponseModel.fail(e.getMessage());
        }
    }

    public static HttpResponseModel andResponse(String param, Function<Map, Object> function) {
        return andResponse(param, function, "success", true);
    }

}
