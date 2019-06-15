package com.jiajiayue.all.regiondrp.biz.dto.response;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author WangHao
 * @date 2019/5/26 1:49
 */
@Component
@Data
public class MockResponse {

    private String storeCode;
    private String skuCode;
    private Date TickTime;
    private int quantity;

}
