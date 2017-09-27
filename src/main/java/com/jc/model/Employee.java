package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee implements Serializable {
    private static final long serialVersionUID = 9222366878800339736L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 部门
     */
    private String department;

    /**
     * 姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 英文名
     */
    @Column(name = "english_name")
    private String englishName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    @Column(name = "extra")
    private String extra;

    @Column(name = "enable")
    private String enable;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", department='" + department + '\'' +
                ", realName='" + realName + '\'' +
                ", englishName='" + englishName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", extra='" + extra + '\'' +
                ", enable='" + enable + '\'' +
                '}';
    }
}