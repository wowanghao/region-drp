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
public class MktStockResponse {

    private String storecode;

    private String skucode;

    private Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone="GMT+8")
    private Date lastUpdateTime;

    private BigDecimal num;

}
