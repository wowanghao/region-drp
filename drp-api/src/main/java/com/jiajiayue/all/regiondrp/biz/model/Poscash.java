package com.jiajiayue.all.regiondrp.biz.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class Poscash {

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

    private Date lastUpdateTime;

    private String comtag;

    private String combatchno;

    private String delOk;

    private Date delTime;

}