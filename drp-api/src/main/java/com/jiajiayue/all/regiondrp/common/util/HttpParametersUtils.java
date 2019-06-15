package com.jiajiayue.all.regiondrp.common.util;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author WangHao
 * @date 2019/2/17 22:54
 */
@Builder
@Data
@Slf4j
public class HttpParametersUtils {

    public Map<String, Object> sendHttpRequest(HttpParameters httpParameters) throws Exception {
        HttpResponse httpResponse = null;
        String requestUrl = httpParameters.getRequestUrl();
        String access_token = httpParameters.getAccess_Token();
        String bodyString = JSONObject.toJSONString(httpParameters.getMap());
        try {
            httpResponse = HttpUtil.createPost(requestUrl).body(bodyString)
                    .header("Access-Token", access_token)
                    .contentType("application/json;charset=UTF-8")
                    .timeout(httpParameters.getTimeout())
                    .execute();
            if (null != httpResponse && httpResponse.getStatus() == 200) {
                if (httpResponse.body().equals("true")) {//O2O接口的临时返回格式
                    log.info("requestUrl={},httpResponse={}", requestUrl, httpResponse.toString());
                    Map mapResponseBody = Maps.newHashMap();
                    mapResponseBody.put("success", true);
                    return mapResponseBody;
                }//-------------------------------------------临时
                Map mapResponseBody = JSON.parseObject(httpResponse.body(), Map.class);
                log.info("requestUrl={},httpResponse={}", requestUrl, httpResponse.toString());
                return mapResponseBody;
            }
            throw new Exception(httpResponse.toString());
        } catch (Exception ex) {
            log.error(requestUrl + "\n" + bodyString + "\n" + "异常信息》》》》》", ex);
            throw new Exception((requestUrl + "\n" + bodyString + "\n" + "异常信息》》》》》" + ex.getMessage()).replace("\"", "'"));
        }
    }


    @Data
    public class HttpParameters {
        String requestUrl = null;
        Map map = Maps.newHashMap();
        String Access_Token = null;
        Integer timeout = 600000;
    }
}
