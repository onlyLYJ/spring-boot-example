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
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class MeetingroomServiceImpl implements MeetingroomService {

    @Autowired
    private MeetingroomMapper meetingroomMapper;

    @Override
    public Meetingroom addMeetingroom(MeetingroomVO meetingroomVO) {
        Meetingroom meetingroom = new Meetingroom();
        String roomname = meetingroomVO.getRoomname();
        meetingroom.setRoomname(roomname);

        List<Meetingroom> list = meetingroomMapper.select(meetingroom);
        if (list != null && list.size() > 0)
            throw new MeetingroomException(MeetingroomException.ALREADY_EXIST);

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

        Integer id = meetingroomVO.getId();
        if (id == null || id == 0)
            throw new MeetingroomException(MeetingroomException.NOT_EXIST);
        Meetingroom meetingroom = meetingroomMapper.selectByPrimaryKey(id);
        if (meetingroom == null)
            throw new MeetingroomException(MeetingroomException.NOT_EXIST);
        meetingroom.setRoomname(meetingroomVO.getRoomname());
        meetingroom.setCapacity(meetingroomVO.getCapacity());
        meetingroom.setStatus(meetingroomVO.getStatus());
        meetingroom.setRemark(meetingroomVO.getRemark());
        meetingroom.setUpdateTime(new Date());
        meetingroomMapper.updateByPrimaryKey(meetingroom);
        return true;
    }

    @Override
    public List<Meetingroom> getMeetingroomByName(String roomname) {
        Meetingroom record = new Meetingroom();
        record.setRoomname(roomname);
        return meetingroomMapper.select(record);
    }

    @Override
    public List<Meetingroom> getMeetingroomById(Integer id) {
        Meetingroom record = new Meetingroom();
        record.setId(id);
        return meetingroomMapper.select(record);
    }

    @Override
    public List<Meetingroom> listMeetingroom() {
        return meetingroomMapper.selectAll();
    }

    @Override
    public Meetingroom addMeetingroom(String roomname, Integer capacity, String status, String remark) {
        Meetingroom meetingroom = new Meetingroom();
        meetingroom.setRoomname(roomname);
        meetingroom.setCapacity(capacity);
        meetingroom.setStatus(status);
        Date date = new Date();
        meetingroom.setRemark(remark);
        meetingroom.setCreateTime(date);
        meetingroom.setUpdateTime(date);
        meetingroomMapper.insertUseGeneratedKeys(meetingroom);
        return meetingroom;
    }


    @Override
    public Integer deleteMeetingroomByName(String roomname) {

        List<Meetingroom> list = getMeetingroomByName(roomname);
        int deleteNum = 0;

        if (list == null || list.size() == 0) {
            return deleteNum;
        }

        for (Meetingroom meetingroom : list) {
            deleteNum += meetingroomMapper.delete(meetingroom);
        }

        return deleteNum;
    }

}
