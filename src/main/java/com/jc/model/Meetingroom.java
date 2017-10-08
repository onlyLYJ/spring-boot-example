package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meetingroom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会议室名
     */
    @Column(name = "room_name")
    private String roomName;

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
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private Date updateTime;

    @Override
    public String toString() {
        return "Meetingroom{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", status='" + status + '\'' +
                ", capacity=" + capacity +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}