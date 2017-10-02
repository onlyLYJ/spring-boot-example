package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "meetingroom_book_detail")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
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
     * 审核状态
     * 0待审核，1审核通过，2审核不通过，3取消
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
    private String roomName;

    @Transient
    private String deptName;

    /**
     * 根据会议开始/结束时间修改状态
     * 预定过期状态，0未过期，1过期
     */
    public String getStatus() {
        String status = this.status;
        if ("1".equals(status)) {
            return "已取消";
        }
        Date now = new Date();
        if ("0".equals(status)) {
            if (meetingBeginTime.after(now)) {
                return "未开始";
            }
            if (meetingEndTime.after(now)) {
                return "进行中";
            }
            if (meetingEndTime.before(now)) {
                return "已结束";
            }
        }
        return status;
    }


    /**
     * 根据audit字段返回审核状态
     * 0待审核，1审核通过，2审核不通过，3取消
     */
    public String getAuditStatus() {

        String auditStatus = this.auditStatus;

        if (auditStatus.equals("0")) {
            return "待审核";
        }

        if (auditStatus.equals("1")) {
            return "审核通过";
        }

        if (auditStatus.equals("2")) {
            return "审核不通过";
        }

        log.error("未知审核状态: " + auditStatus);
        return "审核不通过";
    }


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
                ", roomName='" + roomName + '\'' +
                '}';
    }
}