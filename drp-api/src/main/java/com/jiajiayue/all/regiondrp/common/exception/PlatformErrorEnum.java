package com.jiajiayue.all.regiondrp.common.exception;

import lombok.Getter;

public enum PlatformErrorEnum {
    SYSTEM("SYSTEM","系统异常"),
    LOGIN_00010("LOGIN_00010", "登录调取存储过程失败"),
    LOGIN_00020("LOGIN_00020", "获取菜单失败"),
    LOGIN_00030("LOGIN_00030", "获取功能失败"),
    LOGIN_00040("LOGIN_00040", "登录sql抛出未知异常"),
    LOGIN_00050("LOGIN_00050", "记录日志异常"),
    CARD_00010("CARD_00010", "卡号查询不到卡信息"),
    //卡注销
    CARD_CLOSE_00000("CARD_CLOSE_00000","卡注销列表为空"),
    CARD_CLOSE_00010("CARD_CLOSE_00010", "当前卡中有零钱余额"),
    CARD_00030("CARD_00030", "卡信息列表格式验证异常"),
    CARD_CLOSE_00040("CARD_CLOSE_00040", " 更新表CardJfmain失败"),
    CARD_CLOSE_00050("CARD_CLOSE_00050", " 更新表Cardextent失败"),
    CARD_CLOSE_00090("CARD_CLOSE_00090", " 记录CardJflog失败"),
    CARD_CLOSE_000100("CARD_CLOSE_00100", "推送到电子会员异常"),
    CARD_CLOSE_000110("CARD_CLOSE_00110", "推送到卡券异常"),
    CARD_CLOSE_000120("CARD_CLOSE_00120", "推送到O2O异常"),
    //卡换卡
    CARD_CHANGE_00000("CARD_CHANGE_00000","审核推送到电子会员异常"),
    CARD_CHANGE_00010("CARD_CHANGE_00010","审核推送到卡券异常"),
    CARD_CHANGE_00020("CARD_CHANGE_00020","审核推送到O2O异常"),
    CARD_CLOSE_CHANGE_00099("CARD_CLOSE_CHANGE_00099", "电子会员返回卡号不存在"),
    CARD_00060("CARD_00060", " 更新表cardJflog返回0异常"),
    CARD_00070("CARD_00070", " 卡注销推送到电子会员返回异常"),
    CARD_00080("CARD_00080", " 卡注销推送到卡券返回异常"),
    CARD_00085("CARD_00085", " 卡注销推送到O2O返回异常"),
    CARD_00090("CARD_00090", " 卡换卡推送到电子会员返回异常"),
    CARD_00100("CARD_00100", " 卡换卡推送到卡券返回异常"),
    CARD_00110("CARD_00110", " 卡换卡推送到O2O返回异常");

    @Getter
    private String code;

    @Getter
    private String name;

    PlatformErrorEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
