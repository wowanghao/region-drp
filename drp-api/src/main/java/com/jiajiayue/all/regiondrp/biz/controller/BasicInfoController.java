package com.jiajiayue.all.regiondrp.biz.controller;

import com.alibaba.fastjson.JSON;
import com.jiajiayue.all.regiondrp.biz.dto.request.BasicInfoRequest;
import com.jiajiayue.all.regiondrp.biz.service.BasicInfoReadService;
import com.jiajiayue.all.regiondrp.common.annotation.LogMe;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;
import com.jiajiayue.all.regiondrp.common.util.http.HttpGetUtil;
import com.jiajiayue.all.regiondrp.common.util.http.HttpRequestParam;
import com.jiajiayue.all.regiondrp.common.util.http.HttpResponseModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.jiajiayue.all.regiondrp.common.util.http.Invoker.andResponse;

import java.util.Map;

/**
 * @author WangHao
 * @date 2019/5/25 17:31
 */
@Slf4j
@RestController
@RequestMapping("/api/region/basicInfo/")
@CrossOrigin
public class BasicInfoController {

    @Autowired
    BasicInfoReadService basicInfoReadService;

    @ApiOperation(value = "门店商品")
    @GetMapping(value = "listMktShop")
    @LogMe(module = "基本信息", description = "门店商品")
    public ResponseEntity<RestResponse> listMktShop(BasicInfoRequest request) {
        RestResponse resp = basicInfoReadService.listMktShopPaging(request);
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "测试")
    @GetMapping(value = "test")
    @LogMe(module = "test", description = "test stock")
    public ResponseEntity<String> test(@RequestParam String stkId, Integer loop, Integer pageSize, String urlStr) throws Exception {
        HttpGetUtil httpGetUtil = HttpGetUtil.builder().build();
        if (urlStr == null) {
            urlStr = "http://localhost:8086/api/region/basicInfo/listMktStock";
        }
        Map resultMap = null;
        HttpRequestParam httpRequestParam = HttpRequestParam.builder().requestUrl(urlStr).build();
        httpRequestParam.getParaMap().put("pageSize", pageSize);
        httpRequestParam.getParaMap().put("stkId", stkId);
        HttpResponseModel httpResponseModel = null;
        for (int i = 0; i < loop; i++) {
            httpRequestParam.getParaMap().put("pageNo", i + 1);
            String responseBodyStr = httpGetUtil.sendHttpRequest(httpRequestParam);
            httpResponseModel = andResponse(responseBodyStr, param -> {
                return param.get("result");
            });
            log.info(JSON.toJSONString(httpResponseModel));
            log.info(">>>>>>>>>>>>i=" + i);
        }
        return ResponseEntity.ok(JSON.toJSONString(httpResponseModel));
    }

}
