package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Activity implements Serializable {
    private static final long serialVersionUID = 8737272002167661539L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 活动名
     */
    @Column(name = "activity_name")
    private String activityName;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startTime;

    /**
     * 报名开始时间
     */
    @Column(name = "apply_begin_time")
    private Date applyBeginTime;

    /**
     * 报名结束时间
     */
    @Column(name = "apply_end_time")
    private Date applyEndTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 报名人数
     */
    @Column(name = "apply_num")
    private Integer applyNum;

    /**
     * 0 正常 1 关闭
     */
    private String status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private String other;


    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", startTime=" + startTime +
                ", applyBeginTime=" + applyBeginTime +
                ", applyEndTime=" + applyEndTime +
                ", remark='" + remark + '\'' +
                ", applyNum=" + applyNum +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", other='" + other + '\'' +
                '}';
    }
}