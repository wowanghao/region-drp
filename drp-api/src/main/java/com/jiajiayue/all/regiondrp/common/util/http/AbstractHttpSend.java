package com.jiajiayue.all.regiondrp.common.util.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author WangHao
 * @date 2019/2/17 22:54
 */
@Data
@Slf4j
public abstract class AbstractHttpSend {

    public Map<String, Object> sendHttpRequest(HttpParametersModel httpParametersModel) throws Exception {
        HttpResponse httpResponse = null;
        String requestUrl = httpParametersModel.getRequestUrl();
        try {
            httpResponse = createHttpRequest(httpParametersModel).execute();
            if (null != httpResponse && httpResponse.getStatus() == 200) {
                Map mapResponseBody = JSON.parseObject(httpResponse.body(), Map.class);
                log.info("requestUrl={},httpResponse={}", requestUrl, httpResponse.toString());
                return mapResponseBody;
            }
            throw new Exception("httpResponse.getStatus() != 200");
        } catch (Exception ex) {
            throw new Exception((requestUrl + "\n" + httpResponse + "\n" + "异常信息》》》》》" + ex.getMessage()).replace("\"", "'"));
        }
    }

    public abstract HttpRequest createHttpRequest(HttpParametersModel httpParametersModel) throws Exception;

}
