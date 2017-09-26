package com.jc.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

@Data
@ApiModel(description = "会议室VO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeetingroomVO {

    @ApiModelProperty(value = "会议室id")
    private Integer id;
    @ApiModelProperty(value = "会议室名", required = true)
    @NotEmpty(message = "会议室名不能为空")
    private String roomname;
    @ApiModelProperty(value = "可容纳人数", required = true)
    @Min(value = 1, message = "可容纳人数必须为正整数")
    private Integer capacity;
    @ApiModelProperty(value = "使用状态")
    private String status;
    @ApiModelProperty(value = "备注")
    private String remark;


}
