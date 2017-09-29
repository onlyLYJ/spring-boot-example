package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "meetingroom_book_detail")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingroomBookDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 员工id
     */
    @Column(name = "employee_id")
    private Integer employeeId;

    /**
     * 会议室id
     */
    @Column(name = "meetingroom_id")
    private Integer meetingroomId;

    /**
     * 会议开始时间
     */
    @Column(name = "meeting_begin_time")
    private Date meetingBeginTime;

    /**
     * 会议结束时间
     */
    @Column(name = "meeting_end_time")
    private Date meetingEndTime;

    /**
     * 申请原因
     */
    @Column(name = "book_reason")
    private String bookReason;

    /**
     * 与会人数
     */
    @Column(name = "attend_num")
    private Integer attendNum;

    /**
     * 状态 0待审核，1审核通过，2审核不通过，3取消
     */
    @Column(name = "audit_status")
    private String auditStatus;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 申请提交时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 预定过期状态，0未过期，1过期
     */
    @Column(name = "status")
    private String status;

    /**
     * 预定部门id
     */
    @Column(name = "dept_id")
    private Integer deptId;

    @Transient
    private String realName;

    @Transient
    private String roomname;

    @Transient
    private String deptName;

    @Override
    public String toString() {
        return "MeetingroomBookDetail{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", meetingroomId=" + meetingroomId +
                ", meetingBeginTime=" + meetingBeginTime +
                ", meetingEndTime=" + meetingEndTime +
                ", bookReason='" + bookReason + '\'' +
                ", attendNum=" + attendNum +
                ", auditStatus='" + auditStatus + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status='" + status + '\'' +
                ", realName='" + realName + '\'' +
                ", roomname='" + roomname + '\'' +
                '}';
    }
}