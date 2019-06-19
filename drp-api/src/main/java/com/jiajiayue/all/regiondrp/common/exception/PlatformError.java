package com.jiajiayue.all.regiondrp.common.exception;

import lombok.Getter;

public enum PlatformError {

    BASIC_INFO_00010(00010, "stkId is invalid");

    @Getter
    private int index;
    @Getter
    private String name;

    PlatformError(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
