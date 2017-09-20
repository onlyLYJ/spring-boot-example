package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "meetingroom_book_detail")
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingroomBookDetail implements Serializable {

    private static final long serialVersionUID = 8264695156508701409L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 申请人id
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
     * 申请提交时间
     */
    @Column(name = "create_time")
    private java.util.Date createTime;

    /**
     * 审核状态
     * 0待审核，1审核通过，2审核不通过
     */
    @Column(name = "audit_status")
    private String auditStatus;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

}