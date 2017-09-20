package com.jc.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Meetingroom {
    @Id
    private Integer id;

    /**
     * 会议室名
     */
    private String roomname;

    /**
     * 使用状态 0正常 1关闭
     */
    private String status;

    /**
     * 可容纳人数
     */
    private Integer capacity;

    /**
     * 备注
     */
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