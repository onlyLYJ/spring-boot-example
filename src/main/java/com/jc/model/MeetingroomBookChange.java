package com.jc.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "meetingroom_book_change")
@Data
public class MeetingroomBookChange {
    @Id
    private Integer id;

    /**
     * 会议室预定详情id
     */
    @Column(name = "meetingroom_book_details_id")
    private Integer meetingroomBookDetailsId;

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
}