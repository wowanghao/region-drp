package com.jiajiayue.all.regiondrp.biz.service;

import com.jiajiayue.all.regiondrp.biz.dto.line.StockLine;
import com.jiajiayue.all.regiondrp.biz.model.MktStockInit;
import com.jiajiayue.all.regiondrp.common.request.ListRequest;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;

import java.util.List;
import java.util.Map;

/**
 * @author wh
 * @date 2019/05/2016:54
 */
public interface MktStockReadService {

    Long count(String dataSource) throws Exception;

    List<MktStockInit> listByPage(String dataSource, Map<String, Object> criteria);

    RestResponse handle(ListRequest<StockLine> request);
}
