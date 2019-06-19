package com.jiajiayue.all.regiondrp.biz.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiajiayue.all.regiondrp.biz.dao.MktStockInitDao;
import com.jiajiayue.all.regiondrp.biz.dto.line.StockLine;
import com.jiajiayue.all.regiondrp.biz.dto.wrapper.LineWrapper;
import com.jiajiayue.all.regiondrp.biz.dto.wrapper.ListRequestWrapper;
import com.jiajiayue.all.regiondrp.biz.service.MktStockReadService;
import com.jiajiayue.all.regiondrp.common.exception.PlatformError;
import com.jiajiayue.all.regiondrp.common.request.ListRequest;
import com.jiajiayue.all.regiondrp.common.response.RestResponse;
import io.jjy.platform.common.datasource.DynamicDataSourceContext;
import io.terminus.common.model.Response;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wh
 * @date 2018/11/16 16:59
 */
@Slf4j
@Service
public class MktStockReadServiceImpl implements MktStockReadService {

    @Autowired
    private MktStockInitDao mktStockInitDao;

    @Autowired
    private DynamicDataSourceContext dynamicDataSourceContext;


    @Override
    public Long count(String dataSource) throws Exception {
        Response<Long> result = null;
        Long count = null;
        dynamicDataSourceContext.setDataSource(dataSource);
        try {
            result = Response.ok(mktStockInitDao.count());
            count = result.getResult();
        } catch (Exception ex) {
            log.error("dataSource=" + dataSource + " " + new Exception(ex).getMessage());
        } finally {
            dynamicDataSourceContext.clear();
        }
        return count;
    }

    @Override
    public List listByPage(String dataSource, Map<String, Object> criteria) {
        dynamicDataSourceContext.setDataSource(dataSource);
        List resultList = null;
        try {
            resultList = mktStockInitDao.listPaging(criteria);
        } catch (Exception ex) {
            log.error("dataSource=" + dataSource + " " + PlatformError.BASIC_INFO_00010);
            log.error(new Exception(ex).getMessage());
        } finally {
            dynamicDataSourceContext.clear();
        }
        return resultList;
    }

    @Override
    public RestResponse handle(ListRequest<StockLine> request) {
        ListRequestWrapper listRequestWrapper = buildContext(request);
        return buildResponse(listRequestWrapper);
    }

    private ListRequestWrapper buildContext(ListRequest<StockLine> request) {
        ListRequestWrapper listRequestWrapper = new ListRequestWrapper();
        var now = new Date();
        request.getList().stream()
                .map(StockLine::getStoreCode)
                .filter(StringUtils::isNotEmpty) // remove empty values
                .collect(Collectors.toSet()) // remove duplicates
                .forEach(warehoseCode -> {
                });
        request.getList().forEach(stockLine -> {
            if (null == stockLine.getTickTime()) {
                stockLine.setTickTime(now);
            }
            LineWrapper lineWrapper = new LineWrapper(stockLine);
            listRequestWrapper.putLineWrapper(lineWrapper);
            if (StringUtils.isEmpty(stockLine.getSkuCode())) {
                lineWrapper.setStatus(LineWrapper.Status.SKU_NOT_FOUND);
            }
        });
        return listRequestWrapper;
    }

    private RestResponse buildResponse(ListRequestWrapper listRequestWrapper) {
        List<Map<String, Object>> errorMessages = listRequestWrapper.getLineWrappers().stream()
                .map(lineWrapper -> {
                    Map<String, Object> errorLineParams = new ObjectMapper().convertValue(
                            lineWrapper.getStockLine(), new TypeReference<Map<String, Object>>() {
                            });
                    errorLineParams.put("error", lineWrapper.getStatus().getErrorMessage());
                    return errorLineParams;
                }).collect(Collectors.toList());
        RestResponse response = new RestResponse(true);
        if (errorMessages != null && !errorMessages.isEmpty()) {
            response.setError("error with lines");
            response.setResult(errorMessages);
        }
        return response;
    }
}
