package com.jc.constant;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface BaseEnumBehavior {

    String getCode();

    String getMsg();

}
