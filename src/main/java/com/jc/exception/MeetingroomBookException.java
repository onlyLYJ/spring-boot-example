package com.jc.exception;

/**
 * Create by onlyLYJ on 2017/9/23
 **/

public class MeetingroomBookException extends RuntimeException {

    public static final String TIME_CONFLICT = "预定时间冲突";
    public static final String INVALID_DATA = "数据错误";
    public static final String UPDATE_FAILED = "预定更新失败";

    public MeetingroomBookException() {
    }

    public MeetingroomBookException(String message) {
        super(message);
    }


}
