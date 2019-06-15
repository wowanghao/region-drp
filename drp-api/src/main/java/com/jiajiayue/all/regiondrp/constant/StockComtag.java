package com.jiajiayue.all.regiondrp.constant;

import lombok.Getter;

public enum StockComtag {

    NOTYET(0, "未上传"),
    SUCCESS(1, "成功"),
    FAIL(2, "失败");

    @Getter
    private int index;
    @Getter
    private String name;

    StockComtag(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
