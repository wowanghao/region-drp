package com.jiajiayue.all.regiondrp.biz.controller;

import com.jiajiayue.all.regiondrp.biz.dto.request.BasicInfoRequest;
import com.jiajiayue.all.regiondrp.biz.service.BasicInfoReadService;
import com.jiajiayue.all.regiondrp.common.annotation.LogMe;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
