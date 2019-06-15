package com.jiajiayue.all.regiondrp.biz.service.impl;

import com.jiajiayue.all.regiondrp.biz.dao.MktStockInitDao;
import com.jiajiayue.all.regiondrp.biz.dto.request.MockRequest;
import com.jiajiayue.all.regiondrp.biz.dto.response.MockResponse;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;
import com.jiajiayue.all.regiondrp.biz.model.MktStockInit;
import com.jiajiayue.all.regiondrp.biz.service.MockReadService;
import com.jiajiayue.all.regiondrp.common.model.Paging;
import com.jiajiayue.all.regiondrp.converter.ModelToResponseConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangHao
 * @date 2019/5/25 20:16
 */
@Slf4j
@Service
public class MockReadServiceImpl implements MockReadService {

    @Autowired
    ModelToResponseConverter modelToResponseConverter;

    @Autowired
    MktStockInitDao mktStockInitDao;

    @Override
    public RestResponse listPaging(MockRequest request) {
        List<MockResponse> mktStockInitList = mktStockInitDao.paging(request.toMap())
                .getData().stream()
                .map(modelToResponseConverter::modelToResponse)
                .collect(Collectors.toList());
        RestResponse restResponse = new RestResponse(true);
        restResponse.setResult(new Paging((long) mktStockInitList.size(), mktStockInitList));
        return restResponse;
    }
}
