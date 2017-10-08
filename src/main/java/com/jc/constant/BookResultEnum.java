package com.jc.constant;

import com.jc.model.MeetingroomBookDetail;
import com.jc.vo.MeetingroomBookDetailVO;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 预定结果枚举类
 * Create by onlyLYJ on 2017/9/29
 **/
@Slf4j
public enum BookResultEnum implements BaseEnumBehavior {

    APPLY_SUCCESS("200201", "会议室预定成功"),
    CANCEL_SUCCESS("200202", "取消预定成功"),
    UPDATE_SUCCESS("200203", "预定更新成功"),
    EDIT_AUDIT_SUCCESS("200205", "修改审核状态成功"),
    WEEKLY_APPLY_SUCCESS("200205", "周期预定成功"),

    APPLY_FAILED("200301", "会议室预定失败"),
    TIME_CONFLICT("200302", "预定时间冲突"),
    CANCEL_FAILED("200303", "取消预定失败"),
    UPDATE_FAILED("200303", "预定更新失败"),
    WEEKLY_APPLY_FAILED("200305", "周期预定失败"),
    EDIT_AUDIT_FAILED("200304", "修改审核状态失败"),
    NOT_EXIST("200401", "会议室不存在"),
    INVALID_DATA("200501", "预定时间参数错误"),;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String msg;
    private String code;

    BookResultEnum(String code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    /**
     * 根据List<MeetingroomBookDetailVO>列表构造冲突信息
     *
     * @param conflictList
     * @return
     */
    public static String buildConflictMRBookInfo(List<MeetingroomBookDetailVO> conflictList) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if (conflictList == null || conflictList.size() < 0) {
            log.error("冲突会议室查询错误！");
            return "";
        }

        String roomName = conflictList.get(0).getRoomName();
        StringBuffer sb = new StringBuffer("哎呀，预定时间有冲突啦！\r详情信息：\r会议室名: ").
                append(roomName + "\r");

        for (MeetingroomBookDetail detail : conflictList) {
            sb.append("预定部门： ").append(detail.getDeptName() + "\r");
            sb.append("会议开始时间： ").append(sdf.format(detail.getMeetingBeginTime()) + "\r");
            sb.append("会议结束时间： ").append(sdf.format(detail.getMeetingEndTime()) + "\r\r");
        }
        log.info("冲突会议室信查询成功 ");
        return sb.toString();
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

}
