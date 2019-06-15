package com.jiajiayue.all.regiondrp.common.exception;

import lombok.Getter;

public enum PlatformError {

    STOCK_00010(00010, "库存表count计数错误"),
    STOCK_00020(00020, "库存表list获取数据错误"),
    STOCK_00030(00030, "库存表update更新数据错误"),
    STOCK_00031(00031, "库存表update更新数量为零"),
    STOCK_00040(00040, "发送请求返回超时"),
    STOCK_00050(00050, "发送请求返回非200"),
    STOCK_00060(00060, "解析responseBody格式错误"),
    STOCK_00070(00070, "接口返回错误信息");

    @Getter
    private int index;
    @Getter
    private String name;

    PlatformError(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
