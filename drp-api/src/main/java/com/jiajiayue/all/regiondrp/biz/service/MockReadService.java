package com.jiajiayue.all.regiondrp.biz.service;

import com.jiajiayue.all.regiondrp.biz.dto.request.MockRequest;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;
public interface MockReadService {

    RestResponse listPaging (MockRequest request);
}
