package com.jc.exception;

import com.jc.constant.BaseEnumBehavior;

/**
 * 包装Enum信息Exception
 * Create by onlyLYJ on 2017/10/8
 **/

public class BaseEnumException extends RuntimeException {


    BaseEnumBehavior enumBehavior;

    public BaseEnumException(BaseEnumBehavior enumBehavior) {
        super(enumBehavior.getMsg());
        this.enumBehavior = enumBehavior;
    }

    public BaseEnumBehavior getEnumBehavior() {
        return enumBehavior;
    }

    public void setEnumBehavior(BaseEnumBehavior enumBehavior) {
        this.enumBehavior = enumBehavior;
    }


}
