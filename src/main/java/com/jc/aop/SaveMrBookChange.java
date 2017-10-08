package com.jc.aop;

import java.lang.annotation.*;

/**
 * 记录会议室预定变更
 * Created by onlyLYJ on 2017/9/28.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SaveMrBookChange {
    String name() default "";

}
