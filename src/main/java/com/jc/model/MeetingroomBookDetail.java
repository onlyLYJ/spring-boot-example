package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    private static final long serialVersionUID = -4746534058583100384L;
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
     * 状态
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