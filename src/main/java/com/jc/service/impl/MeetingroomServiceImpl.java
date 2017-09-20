package com.jc.service.impl;

import com.jc.exception.MeetingroomException;
import com.jc.mapper.MeetingroomMapper;
import com.jc.model.Meetingroom;
import com.jc.service.MeetingroomService;
import com.jc.vo.MeetingroomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MeetingroomServiceImpl implements MeetingroomService {

    @Autowired
    private MeetingroomMapper meetingroomMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Meetingroom addMeetingroom(MeetingroomVO meetingroomVO) {
        Meetingroom meetingroom = new Meetingroom();
        String roomname = meetingroomVO.getRoomname();
        meetingroom.setRoomname(roomname);

        List<Meetingroom> list = meetingroomMapper.select(meetingroom);
        if (list != null && list.size() > 0)
            throw new MeetingroomException(String.format(MeetingroomException.ALREADY_EXIST, roomname));

        meetingroom.setCapacity(meetingroomVO.getCapacity());
        meetingroom.setStatus(meetingroomVO.getStatus());
        meetingroom.setRemark(meetingroomVO.getRemark());
        Date date = new Date();
        meetingroom.setCreateTime(date);
        meetingroom.setUpdateTime(date);
        meetingroomMapper.insertUseGeneratedKeys(meetingroom);
        return meetingroom;

    }

    @Override
    public boolean updateMeetingroom(MeetingroomVO meetingroomVO) {
        return false;
    }

    @Override
    public Meetingroom getMeetingroom(String roomname) {
        return null;
    }

    @Override
    public List<Meetingroom> getMeetingroom(Meetingroom meetingroom) {
        return null;
    }

    @Override
    public List<Meetingroom> listMeetingroom() {

        return meetingroomMapper.selectAll();

    }
}
