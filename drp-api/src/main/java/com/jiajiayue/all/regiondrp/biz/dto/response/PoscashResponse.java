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
public class PoscashResponse {

    private String userid;

    private String drpid;

    private String mktid;

    private String name;

    private String duty;

    private String telOther;

    private String password;

    private String office;

    private String codeHelp;

    private String iscash;

    private String isrtn;

    private String ishead;

    private String canuse;

    private String flagCash;

    private String flagDept;

    private String flagUser;

    private String flagShop;

    private String flagTrade;

    private String flagNum;

    private String flagReprint;

    private String flagAudit;

    private String flagTaxprint;

    private String flagCoupon;

    private String flagHang;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone="GMT+8")
    private Date lastUpdateTime;

    private String comtag;

    private String combatchno;

    private String delOk;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone="GMT+8")
    private Date delTime;

}
