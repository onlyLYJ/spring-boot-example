package com.jc.exception;

/**
 * 申请异常
 * Created by onlyLYJ on 2017/9/21.
 */
public class MeetingroomException extends RuntimeException {

    public static final String ALREADY_EXIST = "同名会议室已存在";

    public static final String NOT_EXIST = "需要更新的会议室不存在";


    public MeetingroomException() {
    }

    public MeetingroomException(String message) {
        super(message);
    }
}
