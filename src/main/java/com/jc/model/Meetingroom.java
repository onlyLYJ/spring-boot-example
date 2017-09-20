package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Table(name = "meetingroom")
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meetingroom implements Serializable {
    private static final long serialVersionUID = -7766923892624238382L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会议室名
     */
    @Column(name = "roomname")
    private String roomname;

    /**
     * 使用状态
     * 0正常 1关闭
     */
    @Column(name = "status")
    private String status;

    /**
     * 可容纳人数
     */
    @Column(name = "capacity")
    private Integer capacity;

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