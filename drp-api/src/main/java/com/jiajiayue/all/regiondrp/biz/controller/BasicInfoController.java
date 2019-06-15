package com.jiajiayue.all.regiondrp.biz.controller;

import com.jiajiayue.all.regiondrp.biz.dto.request.BasicInfoRequest;
import com.jiajiayue.all.regiondrp.biz.service.BasicInfoReadService;
import com.jiajiayue.all.regiondrp.biz.service.MktStockReadService;
import com.jiajiayue.all.regiondrp.common.annotation.LogMe;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;
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

    @Autowired
    MktStockReadService mktStockReadService;

    @GetMapping(value = "listNorprice")
    @LogMe(module = "基本信息", description = "正常售价生效(包含专柜")
    public ResponseEntity<RestResponse> listNorprice(BasicInfoRequest request) {
        RestResponse resp = basicInfoReadService.listNorpricePaging(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping(value = "listNorpriceDel")
    @LogMe(module = "基本信息", description = "正常调价失效(包含专柜)")
    public ResponseEntity<RestResponse> listNorpriceDel(BasicInfoRequest request) {
        RestResponse resp = basicInfoReadService.listNorpriceDelPaging(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping(value = "listMktShop")
    @LogMe(module = "基本信息", description = "门店商品")
    public ResponseEntity<RestResponse> listMktShop(BasicInfoRequest request) {
        RestResponse resp = basicInfoReadService.listMktShopPaging(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping(value = "listMktStock")
    @LogMe(module = "基本信息", description = "门店库存")
    public ResponseEntity<RestResponse> listMktStock(BasicInfoRequest request) {
        RestResponse resp = basicInfoReadService.listMktStockPaging(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping(value = "listRentContactC")
    @LogMe(module = "基本信息", description = "专柜商品")
    public ResponseEntity<RestResponse> listRentContactC(BasicInfoRequest request) {
        RestResponse resp = basicInfoReadService.listRentContactCPaging(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping(value = "listPoscash")
    @LogMe(module = "基本信息", description = "收银员定义")
    public ResponseEntity<RestResponse> listPoscash(BasicInfoRequest request) {
        RestResponse resp = basicInfoReadService.listPoscashPaging(request);
        return ResponseEntity.ok(resp);
    }

}
