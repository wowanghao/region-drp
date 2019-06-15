package com.jiajiayue.all.regiondrp.biz.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class MktShop {
    private String storecode;

    private String skucode;

    private Integer offlinesalestatus;

    private Integer onlinesalestatus;

    private Integer price;

    private Date lastUpdateTime;

    private Integer comtag;

    private String combatchno;

    private String stopWsale;

    private String stopWsalertn;

    private String stopDeli;

    private String stopDelirtn;

    private String stopOrder;

    private String stopOrderRtn;

    private String stopMovein;

    private String stopMoveout;

    private String flagDist;

    private String flagDistrtn;

    private String orderStk;

    private String stkidAdd;

    private String orderDeli;

    private String flagDeli;

    private String orderSup;

    private String flagSup;

    private String orderDelisup;

    private String flagDelisup;

    private String drpid;

    private String countid;

    private String deptid;

    private String lockCheck;

    private String defBy;

    private Date timeDef;

    private String chgBy;

    private Date timeChg;

    private String supidLast;

    private Date timeLast;

    private BigDecimal priceLast;

    private BigDecimal numLast;

    private BigDecimal priceSaleMax;

    private BigDecimal priceSaleMin;

    private String flagAutofill;

    private BigDecimal numLocation;

    private BigDecimal dayFill;

    private String dutyBy;

    private BigDecimal parmFill;

    private String typeAbc;

    private String wayLocation;

    private BigDecimal numHoliday;

    private String flagCross;

    private BigDecimal priceLower;

    private Date timeLower;

    private BigDecimal priceAvg;

    private Date timeAvg;

    private String wayBk;

    private String statusShop;

    private Date timeStatchg;

    private String typeKcstk;

    private String reason;

    private String supid1;

    private String supid2;

    private Integer pogMark;

    private String styleid;

    private Integer maxDeep;

    private String pogId;

    private String pogVersion;

    private Date pogDate;

    private Date shopstatchgTime;

    private String freshKf;

    private Date timeKf;

    private byte[] lastmodify;
}