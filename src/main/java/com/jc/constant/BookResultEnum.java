package com.jc.constant;

/**
 * yu
 * Create by onlyLYJ on 2017/9/29
 **/

public enum BookResultEnum {
    NOT_EXIST(501, "会议室不存在"),
    UPDATE_SUCCESS(502, "同名会议室已存在");

    private String desc;
    private int code;

    BookResultEnum(int code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
