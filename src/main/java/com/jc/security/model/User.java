package com.jc.security.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class User {
    @Id
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 部门
     */
    private String department;

    /**
     * 中文名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 其他
     */
    private String extra;

    /**
     * 可用状态 0可用 1不可用
     */
    private String enable;
}