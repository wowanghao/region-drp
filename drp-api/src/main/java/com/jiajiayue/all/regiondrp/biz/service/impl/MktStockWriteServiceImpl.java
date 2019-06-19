package com.jiajiayue.all.regiondrp.biz.service.impl;

import com.jiajiayue.all.regiondrp.biz.dao.MktStockInitDao;
import com.jiajiayue.all.regiondrp.biz.service.MktStockWriteService;
import com.jiajiayue.all.regiondrp.constant.StockComtag;
import io.jjy.platform.common.datasource.DynamicDataSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huxiaohui
 * @date 2018/11/16 16:59
 */
@Slf4j
@Service
public class MktStockWriteServiceImpl implements MktStockWriteService {

    @Autowired
    private MktStockInitDao mktStockInitDao;

    @Autowired
    private DynamicDataSourceContext dynamicDataSourceContext;

    @Override
    public void batchUpdate(String dataSource, List list, StockComtag stockComtag) {
        dynamicDataSourceContext.setDataSource(dataSource);
        try {
            mktStockInitDao.batchUpdate(list, stockComtag);
        } finally {
            dynamicDataSourceContext.clear();
        }
    }
}
