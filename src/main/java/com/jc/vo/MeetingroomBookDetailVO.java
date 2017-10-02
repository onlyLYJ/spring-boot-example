package com.jc.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jc.model.MeetingroomBookDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

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
    private Integer id;
    @ApiModelProperty(value = "员工id")
    private Integer employeeId;
    @ApiModelProperty(value = "会议室id")
    private Integer meetingroomId;
    @ApiModelProperty(value = "员工真实姓名")
    private String realName;
    @ApiModelProperty(value = "会议室名")
    private String roomName;
    @ApiModelProperty(value = "会议开始时间", required = true)
    @Future
    private Date meetingBeginTime;
    @ApiModelProperty(value = "会议结束时间", required = true)
    @Future
    private Date meetingEndTime;
    @ApiModelProperty(value = "申请原因", required = true)
    @NotEmpty(message = "申请原因，不能为空")
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
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "是否周期预定")
    private Boolean isWeeklyBook;
    @ApiModelProperty(value = "周期预定结束时间")
    private Date weeklyBookEndDate;
    @ApiModelProperty(value = "预定部门id")
    private Integer deptId;
    @ApiModelProperty(value = "预定部门名称")
    private String deptName;


    @Override
    public String toString() {
        return "MeetingroomBookDetailVO{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", meetingroomId=" + meetingroomId +
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

        vo.setEmployeeId(this.id);
        vo.setMeetingroomId(this.meetingroomId);
        vo.setMeetingBeginTime(this.meetingBeginTime);
        vo.setMeetingEndTime(this.meetingEndTime);
        vo.setBookReason(this.bookReason);
        vo.setAttendNum(this.attendNum);
        vo.setDeptName(this.deptName);
        vo.setDeptId(this.deptId);
        Date date = new Date();
        vo.setCreateTime(date);
        vo.setUpdateTime(date);

        vo.setStatus(this.status);
        vo.setRemark(this.remark);
        vo.setAuditStatus(this.auditStatus);
        return vo;
    }

}
