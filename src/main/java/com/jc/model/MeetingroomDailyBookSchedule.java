package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "meetingroom_daily_book_schedule")
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingroomDailyBookSchedule implements Serializable {

    private static final long serialVersionUID = 1434187818183347552L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会议室id
     */
    @Column(name = "meetingroom_id")
    private Integer meetingroomId;

    /**
     * 会议的日期
     */
    private java.sql.Date date;

    /**
     * 版本控制字段
     */
    private Integer version;

    /**
     * 版本控制字段
     */
    @Column(name = "other_time")
    private String otherTime;

    /**
     * 早上9点到9预定情况
     * 记录预定详情的id
     */
    private Integer _0900_0930;

    private Integer _0930_1000;

    private Integer _1000_1030;

    private Integer _1030_1100;

    private Integer _1100_1130;

    private Integer _1130_1200;

    private Integer _1200_1230;

    private Integer _1230_1300;

    private Integer _1300_1330;

    private Integer _1330_1400;

    private Integer _1400_1430;

    private Integer _1430_1500;

    private Integer _1500_1530;

    private Integer _1530_1600;

    private Integer _1600_1630;

    private Integer _1630_1700;

    private Integer _1700_1730;

    private Integer _1730_1800;

    private Integer _1800_1830;

    private Integer _1830_1900;

    private Integer _1900_1930;

    private Integer _1930_2000;

    private Integer _2000_2030;

    private Integer _2030_2100;

}