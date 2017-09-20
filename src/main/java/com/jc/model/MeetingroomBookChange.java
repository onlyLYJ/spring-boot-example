package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "meetingroom_book_change")
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingroomBookChange implements Serializable {
    private static final long serialVersionUID = -3693055612615484584L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会议室预定详情id
     */
    @Column(name = "meetingroom_book_details_id")
    private Integer meetingroomBookDetailId;

    /**
     * 预定变化原因
     */
    @Column(name = "change_reason")
    private String changeReason;

    /**
     * 状态
     * 0待审核，1审核通过，2审核不通过, 3取消
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