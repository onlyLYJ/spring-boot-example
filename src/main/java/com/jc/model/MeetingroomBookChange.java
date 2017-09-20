package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "meetingroom_book_change")
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingroomBookChange implements Serializable {
    private static final long serialVersionUID = 2823647931860579819L;
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
     *提交变化的时间
     */
    @Column(name = "create_time")
    private java.util.Date createTime;

    /**
     * 审核状态
     * 0待审核，1审核通过，2审核不通过
     */
    @Column(name = "audit_status")
    private String auditStatus;

}