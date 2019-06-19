package com.jiajiayue.all.regiondrp.biz.service.impl;

import com.jiajiayue.all.regiondrp.biz.dao.*;
import com.jiajiayue.all.regiondrp.biz.dto.request.BasicInfoRequest;
import com.jiajiayue.all.regiondrp.biz.dto.response.*;
import com.jiajiayue.all.regiondrp.biz.service.BasicInfoReadService;
import com.jiajiayue.all.regiondrp.common.model.Paging;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;
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
public class BasicInfoReadServiceImpl implements BasicInfoReadService {

    @Autowired
    ModelToResponseConverter modelToResponseConverter;

    @Autowired
    MktShopDao mktShopDao;

    @Override
    public RestResponse listMktShopPaging(BasicInfoRequest request) {
        request.setSort("skucode");
        List<MktShopResponse> returnResList = mktShopDao.paging(request.toMap())
                .getData().stream()
                .map(modelToResponseConverter::modelToResponse)
                .collect(Collectors.toList());
        RestResponse restResponse = new RestResponse(true);
        restResponse.setResult(new Paging(mktShopDao.paging(request.toMap()).getTotal(), returnResList));
        return restResponse;
    }

}
