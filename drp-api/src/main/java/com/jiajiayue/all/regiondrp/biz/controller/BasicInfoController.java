package com.jiajiayue.all.regiondrp.biz.controller;

import com.alibaba.fastjson.JSON;
import com.jiajiayue.all.regiondrp.biz.dto.request.BasicInfoRequest;
import com.jiajiayue.all.regiondrp.biz.service.BasicInfoReadService;
import com.jiajiayue.all.regiondrp.common.annotation.LogMe;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;
import com.jiajiayue.all.regiondrp.common.util.http.HttpGetUtil;
import com.jiajiayue.all.regiondrp.common.util.http.HttpParametersModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> test(@RequestParam String stkId, Integer loop, Integer pageSize) throws Exception {
        HttpGetUtil httpGetUtil = HttpGetUtil.builder().build();
        String urlStr = "http://localhost:8086/api/region/basicInfo/listMktStock";
        Map resultMap = null;
        HttpParametersModel httpParametersModel = HttpParametersModel.builder().requestUrl(urlStr).build();
        httpParametersModel.getParaMap().put("pageSize", pageSize);
        httpParametersModel.getParaMap().put("stkId", stkId);
        for (int i = 0; i < loop; i++) {
            httpParametersModel.getParaMap().put("pageNo", i+1);
            resultMap = httpGetUtil.sendHttpRequest(httpParametersModel);
            log.info(JSON.toJSONString(resultMap));
            log.info(">>>>>>>>>>>>i=" + i);
        }
        return ResponseEntity.ok(JSON.toJSONString(resultMap));
    }

}
