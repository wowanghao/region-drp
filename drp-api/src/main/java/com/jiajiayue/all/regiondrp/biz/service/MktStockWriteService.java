package com.jiajiayue.all.regiondrp.biz.service;

import com.jiajiayue.all.regiondrp.constant.StockComtag;

import java.util.List;

public interface MktStockWriteService {
    void batchUpdate(String dataSource, List mktStock, StockComtag StockComtag);
}
