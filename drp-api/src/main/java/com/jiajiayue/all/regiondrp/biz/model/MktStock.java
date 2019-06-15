package com.jiajiayue.all.regiondrp.biz.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class MktStock {

    private String storecode;

    private String skucode;

    private Integer quantity;

    private Date lastUpdateTime;

    private Integer comtag;

    private String combatchno;

    private BigDecimal num;
}