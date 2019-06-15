package com.jiajiayue.all.regiondrp.biz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiajiayue.all.regiondrp.biz.dto.request.DrpInventorySyncRequest;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author WangHao
 * @date 2019/5/21 17:31
 */
@Slf4j
@RestController
@RequestMapping("/test")
@CrossOrigin
public class StockController {


    @RequestMapping(value = "init", method = { RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<RestResponse> initStoreSkuInventoryWithRatio
            (@RequestBody DrpInventorySyncRequest request) throws Exception {
        String reqStr = new ObjectMapper().writeValueAsString(request);
        try {
            //var resp = mockService.doSomething(request);
           // return ResponseEntity.ok(resp);
            return null;
        } catch (Exception e) {
            log.error("[ inventory.init ] >> request = {}, error = {}", reqStr, e);
            var resp = new RestResponse(e);
            return ResponseEntity.status(500).body(resp);
        }
    }
}
