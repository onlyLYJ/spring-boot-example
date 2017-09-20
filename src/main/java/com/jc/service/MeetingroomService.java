package com.jc.service;

import com.jc.model.Meetingroom;
import com.jc.vo.MeetingroomVO;

import java.util.List;

/**
 * Created by onlyLYJ on 2017/9/20.
 */
public interface MeetingroomService {

    /**
     * 增加会议室
     *
     * @param meetingroomVO
     * @return
     */
    Meetingroom addMeetingroom(MeetingroomVO meetingroomVO);

    /**
     * 更新会议室信息
     *
     * @param meetingroomVO
     * @return
     */
    boolean updateMeetingroom(MeetingroomVO meetingroomVO);

    /**
     * 根据会议室名称获得会议室
     *
     * @param roomname
     * @return
     */
    Meetingroom getMeetingroom(String roomname);


    List<Meetingroom> getMeetingroom(Meetingroom meetingroom);

    /**
     * 获取所有会议室列表
     *
     * @return
     */
    List<Meetingroom> listMeetingroom();
}
