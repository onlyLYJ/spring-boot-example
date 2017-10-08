package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "meetingroom_book_change")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingroomBookChange implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会议室预定详情id
     */
    @Column(name = "meetingroom_book_detail_id")
    private Integer meetingroomBookDetailId;

    /**
     * 预定变化原因
     */
    @Column(name = "change_reason")
    private String changeReason;

    /**
     * 状态 0待审核，1审核通过，2审核不通过, 3取消
     */
    @Column(name = "audit_status")
    private String auditStatus;

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
     * 申请人id
     */
    @Column(name = "employee_id")
    private Integer employeeId;


    @Override
    public String toString() {
        return "MeetingroomBookChange{" +
                "id=" + id +
                ", meetingroomBookDetailId=" + meetingroomBookDetailId +
                ", changeReason='" + changeReason + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }

    public MeetingroomBookChange clone() {

        MeetingroomBookChange record = new MeetingroomBookChange();
        record.setId(null);
        record.setMeetingroomBookDetailId(null);
        record.setEmployeeId(this.employeeId);
        record.setChangeReason(this.changeReason);
        record.setAuditStatus(this.auditStatus);
        record.setCreateTime(this.getCreateTime());
        record.setUpdateTime(this.getUpdateTime());
        return record;
    }


}