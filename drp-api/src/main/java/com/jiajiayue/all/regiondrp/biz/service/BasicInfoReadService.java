package com.jiajiayue.all.regiondrp.biz.service;

import com.jiajiayue.all.regiondrp.biz.dto.request.BasicInfoRequest;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;

public interface BasicInfoReadService {

    RestResponse listNorpricePaging(BasicInfoRequest request);

    RestResponse listNorpriceDelPaging(BasicInfoRequest request);

    RestResponse listMktShopPaging(BasicInfoRequest request);

    RestResponse listMktStockPaging(BasicInfoRequest request);

    RestResponse listRentContactCPaging(BasicInfoRequest request);

    RestResponse listPoscashPaging(BasicInfoRequest request);
}
