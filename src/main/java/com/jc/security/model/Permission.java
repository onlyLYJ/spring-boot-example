package com.jc.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Id;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission {
    @Id
    private Integer id;
    /**
     * 权限名
     */
    private String name;
    /**
     * 介绍
     */
    private String description;

    /**
     * 链接
     */
    private String url;

    /**
     * 调用方法
     */
    private String method;

}