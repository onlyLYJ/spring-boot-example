package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "department")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 部门名称
     */
    @Column(name = "dept_name")
    private String deptName;

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
     * 可用情况 0可用 1不可用
     */
    private String enable;

    /**
     * 备用字段
     */
    private String extra;

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", deptName='" + deptName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", enable='" + enable + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}