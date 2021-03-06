package com.jiajiayue.all.regiondrp.biz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yushuo
 */

@Data
public class DrpInventoryLine implements Serializable {

    private String storeCode;

    private String skuCode;

    private Long quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Asia/Shanghai")
    private Date tickTime;

}
