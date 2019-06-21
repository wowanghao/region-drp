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
@Builder
@Data
@Slf4j
public class HttpGetUtil extends AbstractHttpSend {

    @Override
    public HttpRequest createHttpRequest(HttpParametersModel httpParametersModel) throws Exception {
        String requestUrl = httpParametersModel.getRequestUrl();
        HttpRequest httpRequest = HttpUtil.createGet(requestUrl)
                .form(httpParametersModel.getParaMap())
                .timeout(httpParametersModel.getTimeout());
        Map<String, String> headerMap = httpParametersModel.getHeaderMap();
        for (String key : headerMap.keySet()) {
            httpRequest = httpRequest.header(key, headerMap.get(key));
        }
        return httpRequest;
    }

}
