package com.jiajiayue.all.regiondrp.biz.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huxiaohui
 * @date 2018/11/16 15:57
 */
@Data
public class MktStockInit implements Serializable {
    private String storeCode;
    private String skuCode;
    private Date TickTime;
    private int quantity;
    private int Comtag;//0未上传，1成功，2失败
    private String error;//对应数据库的 Combatchno

    public String getId() {
        return this.storeCode + "_" + this.skuCode;
    }
}
