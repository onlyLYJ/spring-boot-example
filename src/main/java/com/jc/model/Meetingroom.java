package com.jc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Table(name = "meetingroom")
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meetingroom implements Serializable {
    private static final long serialVersionUID = 5132145423398503080L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会议室名
     */
    private String roomname;

    /**
     * 使用状态
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

}