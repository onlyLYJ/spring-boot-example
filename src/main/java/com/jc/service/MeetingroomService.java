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
    Integer updateMeetingroom(MeetingroomVO meetingroomVO);

    /**
     * 根据会议室名称查找
     *
     * @param roomname
     * @return
     */
    List<Meetingroom> getMeetingroomByName(String roomname);

    /**
     * 获取所有会议室列表
     *
     * @return
     */
    List<Meetingroom> listMeetingroom();

    List<Meetingroom> getMeetingroomById(Integer id);

    Integer deleteMeetingroomById(Integer id);
}
