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
    NorpriceDao norpriceDao;

    @Autowired
    NorpriceDelDao norpriceDelDao;

    @Autowired
    MktShopDao mktShopDao;

    @Autowired
    MktStockDao mktStockDao;

    @Autowired
    RentContactCDao rentContactCDao;

    @Autowired
    PoscashDao poscashDao;

    @Override
    public RestResponse listNorpricePaging(BasicInfoRequest request) {
        request.setSort("bill");
        List<NorpriceResponse> returnResList = norpriceDao.paging(request.toMap())
                .getData().stream()
                .map(modelToResponseConverter::modelToResponse)
                .collect(Collectors.toList());
        RestResponse restResponse = new RestResponse(true);
        restResponse.setResult(new Paging(norpriceDao.paging(request.toMap()).getTotal(), returnResList));
        return restResponse;
    }

    @Override
    public RestResponse listNorpriceDelPaging(BasicInfoRequest request) {
        request.setSort("bill");
        List<NorpriceDelResponse> returnResList = norpriceDelDao.paging(request.toMap())
                .getData().stream()
                .map(modelToResponseConverter::modelToResponse)
                .collect(Collectors.toList());
        RestResponse restResponse = new RestResponse(true);
        restResponse.setResult(new Paging(norpriceDelDao.paging(request.toMap()).getTotal(), returnResList));
        return restResponse;
    }

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

    @Override
    public RestResponse listMktStockPaging(BasicInfoRequest request) {
        request.setSort("skucode");
        List<MktStockResponse> returnResList = mktStockDao.paging(request.toMap())
                .getData().stream()
                .map(modelToResponseConverter::modelToResponse)
                .collect(Collectors.toList());
        RestResponse restResponse = new RestResponse(true);
        restResponse.setResult(new Paging(mktStockDao.paging(request.toMap()).getTotal(), returnResList));
        return restResponse;
    }

    @Override
    public RestResponse listRentContactCPaging(BasicInfoRequest request) {
        request.setSort("shopid");
        List<RentContactCResponse> returnResList = rentContactCDao.paging(request.toMap())
                .getData().stream()
                .map(modelToResponseConverter::modelToResponse)
                .collect(Collectors.toList());
        RestResponse restResponse = new RestResponse(true);
        restResponse.setResult(new Paging(rentContactCDao.paging(request.toMap()).getTotal(), returnResList));
        return restResponse;
    }

    @Override
    public RestResponse listPoscashPaging(BasicInfoRequest request) {
        request.setSort("userid");
        List<PoscashResponse> returnResList = poscashDao.paging(request.toMap())
                .getData().stream()
                .map(modelToResponseConverter::modelToResponse)
                .collect(Collectors.toList());
        RestResponse restResponse = new RestResponse(true);
        restResponse.setResult(new Paging(poscashDao.paging(request.toMap()).getTotal(), returnResList));
        return restResponse;
    }
}
