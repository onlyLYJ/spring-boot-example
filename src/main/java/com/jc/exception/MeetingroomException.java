package com.jc.exception;

/**
 * 申请异常
 * Created by onlyLYJ on 2017/9/21.
 */
public class MeetingroomException extends RuntimeException {

    public static final String ALREADY_EXIST = "同名会议室: {} 已存在";

    public MeetingroomException() {
    }

    public MeetingroomException(String message) {
        super(message);
    }
}
