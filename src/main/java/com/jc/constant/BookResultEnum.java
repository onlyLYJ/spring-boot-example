package com.jc.constant;

import com.jc.model.MeetingroomBookDetail;
import com.jc.vo.MeetingroomBookDetailVO;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * yu
 * Create by onlyLYJ on 2017/9/29
 **/

public enum BookResultEnum implements BaseEnumBehavior {

    APPLY_SUCCESS("200201", "会议室预定成功"),
    DELETE_SUCCESS("200202", "预定删除成功"),
    UPDATE_SUCCESS("200203", "预定更新成功"),
    CANCEL_SUCCESS("200204", "取消预定成功"),
    EDIT_AUDIT_SUCCESS("200205", "修改审核状态成功"),
    WEEKLY_APPLY_SUCCESS("200205", "周期预定成功"),
    TIME_CONFLICT("200301", "预定时间冲突"),
    CANCEL_FAILED("200302", "取消预定失败"),
    EDIT_AUDIT_FAILED("200303", "修改审核状态失败"),
    NOT_EXIST("200401", "会议室不存在"),;
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
        if (conflictList == null || conflictList.size() < 0)
            return "";
        String roomName = conflictList.get(0).getRoomName();
        StringBuffer sb = new StringBuffer("冲突会议室: ").append(roomName + "\r");

        for (MeetingroomBookDetail detail : conflictList) {
            sb.append("预定部门： ").append(detail.getDeptName() + "\r");
            sb.append("会议开始时间： ").append(sdf.format(detail.getMeetingBeginTime()) + "\r");
            sb.append("会议结束时间： ").append(sdf.format(detail.getMeetingEndTime()) + "\r\r");
        }
        return sb.toString();
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getCode() {
        return code;
    }


}
