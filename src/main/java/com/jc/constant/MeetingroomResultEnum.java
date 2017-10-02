package com.jc.constant;

/**
 * yu
 * Create by onlyLYJ on 2017/9/29
 **/

public enum MeetingroomResultEnum implements BaseEnumBehavior {

    ADD_SUCCESS("100201", "增加会议室成功"),
    UPDATE_SUCCESS("100203", "更新会议室信息成功"),
    CANCEL_SUCCESS("100204", "取消会议室成功"),
    DUPLICATE_NAME("100301", "英雄所见略同！不过，会议室名不能重复哟"),
    CANCEL_FAILED("100302", "会议室置位失败"),
    NOT_EXIST("100501", "会议室不存在");
    private String msg;
    private String code;

    MeetingroomResultEnum(String code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getCode() {
        return code;
    }
}
