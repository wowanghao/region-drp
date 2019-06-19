package com.jiajiayue.all.regiondrp.common.exception;

import lombok.Getter;

public enum PlatformErrorEnum {
    SYSTEM("SYSTEM","系统异常"),
    BASIC_INFO_00010("00010", "门店编码无效");

    @Getter
    private String code;

    @Getter
    private String name;

    PlatformErrorEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
