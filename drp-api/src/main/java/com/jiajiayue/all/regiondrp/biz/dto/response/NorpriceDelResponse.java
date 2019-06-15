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
public class NorpriceDelResponse {

    private String bill;

    private String drpid;

    private String groupid;

    private String shopid;

    private BigDecimal priceNew;

    private BigDecimal priceVipnew;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone="GMT+8")
    private Date timeOknew;

    private String billNext;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone="GMT+8")
    private Date lastUpdateTime;

    private String remark;

}
