package com.jiajiayue.all.regiondrp.biz.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WangHao
 * @date 2019/5/26 1:49
 */
@Component
@Data
public class MktShopResponse {

    private String storecode;

    private String skucode;

    private Integer offlinesalestatus;

    private Integer onlinesalestatus;

    private Integer price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date lastUpdateTime;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date timeDef;

    private String chgBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date timeChg;

    private String supidLast;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date timeLower;

    private BigDecimal priceAvg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date timeAvg;

    private String wayBk;

    private String statusShop;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date pogDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date shopstatchgTime;

    private String freshKf;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date timeKf;

}
