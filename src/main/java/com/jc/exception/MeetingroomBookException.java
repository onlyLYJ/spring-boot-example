package com.jc.exception;

import com.jc.constant.BookResultEnum;
import com.jc.model.MeetingroomBookDetail;
import com.jc.vo.MeetingroomBookDetailVO;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Create by onlyLYJ on 2017/9/23
 **/
@Slf4j
public class MeetingroomBookException extends BaseEnumException {


    public MeetingroomBookException(BookResultEnum bookResultEnum) {
        super(bookResultEnum);
    }


    public MeetingroomBookException(List<MeetingroomBookDetailVO> conflictBookList) {

        super(BookResultEnum.TIME_CONFLICT);
        BookResultEnum bookResultEnum = (BookResultEnum) getEnumBehavior();
        bookResultEnum.setMsg(buildConflictMRBookInfo(conflictBookList));

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

}
