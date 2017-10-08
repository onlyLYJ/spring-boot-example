package com.jc.exception;

import com.jc.constant.BaseEnumBehavior;

/**
 * 申请异常
 * Created by onlyLYJ on 2017/9/21.
 */
public class MeetingroomException extends BaseEnumException {

    public MeetingroomException(BaseEnumBehavior meetingroomEnum) {
        super(meetingroomEnum);
    }

}
