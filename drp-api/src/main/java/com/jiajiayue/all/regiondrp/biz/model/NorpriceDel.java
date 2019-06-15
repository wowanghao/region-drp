package com.jiajiayue.all.regiondrp.biz.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class NorpriceDel {

    private String bill;

    private String drpid;

    private String groupid;

    private String shopid;

    private BigDecimal priceNew;

    private BigDecimal priceVipnew;

    private Date timeOknew;

    private String billNext;

    private Date lastUpdateTime;

    private String comtag;

    private String combatchno;

    private String remark;

    private byte[] lastmodify;
}