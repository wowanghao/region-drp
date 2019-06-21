package com.jiajiayue.all.regiondrp.common.util.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WangHao
 * @date 2019/2/17 22:54
 */
@Data
@Slf4j
public abstract class AbstractHttpSend {

    public String sendHttpRequest(HttpRequestParam httpRequestParam) throws Exception {
        HttpResponse httpResponse = null;
        String requestUrl = httpRequestParam.getRequestUrl();
        try {
            httpResponse = createHttpRequest(httpRequestParam).execute();
            if (null != httpResponse && httpResponse.getStatus() == HttpStatus.HTTP_OK) {
                log.info("requestUrl={},httpResponse={}", requestUrl, httpResponse.toString());
                return httpResponse.body();
            }
            throw new Exception("httpResponse.getStatus() != 200");
        } catch (Exception ex) {
            throw new Exception((requestUrl + "\n" + httpResponse + "\n" + "异常信息》》》》》" + ex.getMessage()).replace("\"", "'"));
        }
    }

    public abstract HttpRequest createHttpRequest(HttpRequestParam httpRequestParam) throws Exception;

}
