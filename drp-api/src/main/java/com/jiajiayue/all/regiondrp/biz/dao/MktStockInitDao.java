package com.jiajiayue.all.regiondrp.biz.dao;

import com.jiajiayue.all.regiondrp.biz.model.MktStockInit;
import com.jiajiayue.all.regiondrp.constant.StockComtag;
import io.jjy.platform.common.datasource.dao.DynamicDataSourceMybatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MktStockInitDao extends DynamicDataSourceMybatisDao<MktStockInit> {


    public Long count() {
        return this.sqlSession.selectOne(this.sqlId("count"));
    }

    public List<MktStockInit> listPaging(Map criteria) {
        return this.sqlSession.selectList(this.sqlId("listByPage"), criteria);
    }

    public Integer batchUpdate(List<MktStockInit> list, StockComtag stockComtag) {
        list.stream().map(MktStockInit -> {
            MktStockInit.setComtag(stockComtag.getIndex());
            return MktStockInit;
        }).collect(Collectors.toList());
        return this.sqlSession.update(this.sqlId("batchUpdate"), list);
    }

}
