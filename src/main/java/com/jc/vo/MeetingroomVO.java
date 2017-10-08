package com.jc.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

@Data
@ApiModel(description = "会议室VO")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingroomVO {

    @ApiModelProperty(value = "会议室id")
    @Min(value = 1, message = "会议室信息错误")
    private Integer id;

    @ApiModelProperty(value = "会议室名", required = true)
    @NotBlank(message = "会议室名不能为空")
    private String roomName;

    @ApiModelProperty(value = "可容纳人数", required = true)
    @Min(value = 1, message = "可容纳人数必须为正整数")
    private Integer capacity;

    @ApiModelProperty(value = "使用状态")
    @NotBlank(message = "可使用状态不能为空")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;


}
