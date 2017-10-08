package com.jc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jc.model.MeetingroomBookDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@ApiModel(description = "会议室预定详情VO")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingroomBookDetailVO extends MeetingroomBookDetail implements Cloneable {

    @ApiModelProperty(value = "会议预定详情id")
    @Min(value = 1, message = "提交数据错误")
    private Integer id;

    @ApiModelProperty(value = "会议室id")
    @Min(value = 1, message = "提交数据错误")
    private Integer meetingroomId;

    @ApiModelProperty(value = "员工id")
    @Min(value = 1, message = "提交数据错误")
    private Integer employeeId;

    @ApiModelProperty(value = "员工真实姓名")
    @Length(min = 1, max = 25, message = "姓名必须为1-25个字符")
    private String realName;

    @ApiModelProperty(value = "会议室名")
    @Length(min = 1, max = 25, message = "会议室名无效，须为1-25个字符")
    private String roomName;

    @ApiModelProperty(value = "会议开始时间", required = true)
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @Future(message = "会议开始时间错误，穿越回去开会嘛？")
    private Date meetingBeginTime;

    @ApiModelProperty(value = "会议结束时间", required = true)
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @Future(message = "会议结束时间错误，穿越回去开会嘛？")
    private Date meetingEndTime;

    @ApiModelProperty(value = "申请原因", required = true)
    @NotBlank(message = "申请原因不能为空")
    private String bookReason;

    @ApiModelProperty(value = "与会人数")
    @Min(value = 1, message = "与会人数必须为正整数")
    private Integer attendNum;

    @ApiModelProperty(value = "预定过期状态 0未过期，1过期")
    private String status;

    @ApiModelProperty(value = "审核状态 0待审核，1审核通过，2审核不通过，3取消")
    private String auditStatus;

    @ApiModelProperty(value = "使用状态")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "是否周期预定")
    private Boolean isWeeklyBook;

    @ApiModelProperty(value = "周期预定结束时间")
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm")
    @Future(message = "会议周期结束时间错误，穿越回去开会嘛？")
    private Date weeklyBookEndDate;

    @ApiModelProperty(value = "预定部门id")
    @Min(value = 1, message = "部门信息错误")
    private Integer deptId;

    @ApiModelProperty(value = "预定部门名称")
    private String deptName;

    @Override
    public String toString() {
        return "MeetingroomBookDetailVO{" +
                "id=" + id +
                ", meetingroomId=" + meetingroomId +
                ", employeeId=" + employeeId +
                ", realName='" + realName + '\'' +
                ", roomName='" + roomName + '\'' +
                ", meetingBeginTime=" + meetingBeginTime +
                ", meetingEndTime=" + meetingEndTime +
                ", bookReason='" + bookReason + '\'' +
                ", attendNum=" + attendNum +
                ", status='" + status + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isWeeklyBook=" + isWeeklyBook +
                ", weeklyBookEndDate=" + weeklyBookEndDate +
                ", deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                '}';
    }

    public MeetingroomBookDetailVO clone() {
        MeetingroomBookDetailVO vo = new MeetingroomBookDetailVO();

        vo.setEmployeeId(this.employeeId);
        vo.setMeetingroomId(this.meetingroomId);
        vo.setDeptId(this.deptId);
        vo.setMeetingBeginTime(this.meetingBeginTime);
        vo.setMeetingEndTime(this.meetingEndTime);
        vo.setBookReason(this.bookReason);
        vo.setAttendNum(this.attendNum);
        Date date = new Date();
        vo.setCreateTime(date);
        vo.setUpdateTime(date);
        vo.setStatus(this.status);
        vo.setRemark(this.remark);
        vo.setAuditStatus(this.auditStatus);

        return vo;
    }

}
