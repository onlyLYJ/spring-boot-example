package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "activity_apply")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityApply implements Serializable {
    private static final long serialVersionUID = 8731165141504751963L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 活动id
     */
    @Column(name = "activity_id")
    private Integer activityId;

    /**
     * 员工id
     */
    @Column(name = "employee_id")
    private Integer employeeId;

    /**
     * 活动名
     */
    @Column(name = "activity_name")
    private String activityName;

    /**
     * 真是姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0 报名 1 取消报名
     */
    private String status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private String department;

    @Transient
    private String englishName;

    @Override
    public String toString() {
        return "ActivityApply{" +
                "id=" + id +
                ", activityId=" + activityId +
                ", employeeId=" + employeeId +
                ", activityName='" + activityName + '\'' +
                ", realName='" + realName + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", department='" + department + '\'' +
                ", englishName='" + englishName + '\'' +
                '}';
    }
}