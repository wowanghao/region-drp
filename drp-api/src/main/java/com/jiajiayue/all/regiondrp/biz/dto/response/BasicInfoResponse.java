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
public class BasicInfoResponse {

    private String storecode;

    private String skucode;

    private Integer offlinesalestatus;

    private Integer onlinesalestatus;

    private Integer price;

    private Date createtime;

}
