package com.jiajiayue.all.regiondrp.biz.model;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class RentContactC {

    private String noContact;

    private String drpid;

    private String mktid;

    private String custid;

    private String shopid;

    private String nameFull;

    private String nameShort;

    private String classid;

    private String shopidVs;

    private String standards;

    private String unitSale;

    private String flagWp;

    private String txm;

    private BigDecimal price;

    private BigDecimal disc;

    private BigDecimal num;

    private BigDecimal discNum;

    private BigDecimal discMax;

    private String canrtn;

    private String stopSale;

    private BigDecimal pointBack;

    private BigDecimal taxBuy;

    private BigDecimal taxSale;

    private String deptid;

    private String defBy;

    private Date timeDef;

    private String chgBy;

    private Date lastUpdateTime;

    private BigDecimal pointBackpro;

    private String deptidBy;

    private String trademark;

    private String marksmall;

    private String makeBy;

    private String flagTax;

    private BigDecimal pointExceed;

    private BigDecimal toplimitSale;

    private String flagExceed;

    private String flagSale;

    private String flagStop;

    private String reason;

    private Integer yearid;

    private String typeStore;

    private String category;

    private String flagO2o;

    private String sellingPoint;
}