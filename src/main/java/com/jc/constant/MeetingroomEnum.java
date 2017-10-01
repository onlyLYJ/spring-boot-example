package com.jc.constant;

/**
 * 部门枚举
 * Created by jasonzhu on 2017/7/13.
 */
public enum MeetingroomEnum {

    VALID_STATUS("可用", "0"),
    INVALID_STATUS("不可用", "1"),
    WAIT_AUDIT_STATUS("待审核", "0");

    private String code;

    MeetingroomEnum(String msg, String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
