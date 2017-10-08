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
     * 根据会议室名称查找
     *
     * @param roomName
     * @return
     */
    List<Meetingroom> getMeetingroomByName(String roomName);

    /**
     * 获取所有可用会议室列表
     *
     * @return
     */
    List<Meetingroom> getValidMeetingroomList();

    List<Meetingroom> getMeetingroomById(Integer id);

    boolean cancelMeetingroomById(Integer id, Integer employeeId, String changeReason);

    List<Meetingroom> getMeetingroomList();
}
