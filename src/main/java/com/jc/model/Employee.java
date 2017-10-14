package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jc.security.model.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @NotBlank(message = "部门信息不能为空")
    private String department;

    /**
     * 姓名
     */
    @Column(name = "real_name")
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    /**
     * 英文名
     */
    @Column(name = "english_name")
    @NotBlank(message = "用户名不能为空")
    private String englishName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 密码
     */
    @Column(name = "password")
    @Length(min = 6, max = 20, message = "密码必须为6-20位")
    private String password;

    @Column(name = "extra")
    private String extra;

    @Column(name = "enable")
    private String enable;

    @Transient
    private List<Role> roles;

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
                ", roles=" + roles +
                '}';
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }



}