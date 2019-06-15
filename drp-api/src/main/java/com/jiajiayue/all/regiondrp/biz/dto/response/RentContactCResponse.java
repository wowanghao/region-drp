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
public class RentContactCResponse {

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone="GMT+8")
    private Date timeDef;

    private String chgBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone="GMT+8")
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
