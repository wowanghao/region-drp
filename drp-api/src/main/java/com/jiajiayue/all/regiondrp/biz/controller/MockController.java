/*
package com.jiajiayue.all.regiondrp.biz.controller;

import com.jiajiayue.all.regiondrp.biz.dto.line.StockLine;
import com.jiajiayue.all.regiondrp.biz.dto.request.MockRequest;
import com.jiajiayue.all.regiondrp.common.annotation.LogMe;
import com.jiajiayue.all.regiondrp.common.request.ListRequest;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

*/
/**
 * @author WangHao
 * @date 2019/5/25 17:31
 *//*

@Slf4j
@RestController
@RequestMapping("/mock/")
@CrossOrigin
public class MockController {



    @GetMapping(value = "list")
    @LogMe(module = "Mock管理", description = "创建Mock")
    public ResponseEntity<RestResponse> start(MockRequest request) {
        RestResponse resp = mockReadService.listPaging(request);
        return ResponseEntity.ok(resp);
    }

    @PostMapping(value = "list")
    public ResponseEntity<RestResponse> list(@RequestBody ListRequest<StockLine> request) {
        //String reqStr = new ObjectMapper().writeValueAsString(request);
        RestResponse resp = mktStockReadService.handle(request);
        return ResponseEntity.ok(resp);
    }
}
*/
