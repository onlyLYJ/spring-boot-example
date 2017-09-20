package com.jc.service;

import com.jc.model.Meetingroom;

/**
 * Created by onlyLYJ on 2017/9/20.
 */
public interface MeetingroomService {
    /**
     * 增加会议室
     *
     * @param roomname
     * @param status
     * @param capacity
     * @param remark
     * @return
     */
    Meetingroom addMeetingroom(String roomname, String status, Integer capacity, String remark);

    /**
     * 更新会议室信息
     *
     * @param meetingroom
     * @return
     */
    boolean updateMeetingroom(Meetingroom meetingroom);

    /**
     * 根据会议室名称获得会议室
     *
     * @param roomname
     * @return
     */
    Meetingroom getMeetingroom(String roomname);
}
