package com.jc.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "meetingroom_book_detail")
@Data
public class MeetingroomBookDetail {
    @Id
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
}