package com.jiajiayue.all.regiondrp.biz.service;

import com.jiajiayue.all.regiondrp.biz.dto.request.BasicInfoRequest;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;

public interface BasicInfoReadService {

    RestResponse listMktShopPaging(BasicInfoRequest request);

}
