package com.jc.service.impl;

import com.jc.constant.MeetingroomResultEnum;
import com.jc.exception.MeetingroomException;
import com.jc.mapper.MeetingroomBookChangeMapper;
import com.jc.mapper.MeetingroomBookDetailMapper;
import com.jc.mapper.MeetingroomMapper;
import com.jc.model.Meetingroom;
import com.jc.model.MeetingroomBookChange;
import com.jc.service.MeetingroomService;
import com.jc.vo.MeetingroomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class MeetingroomServiceImpl implements MeetingroomService {

    @Autowired
    private MeetingroomMapper mrMapper;

    @Autowired
    private MeetingroomBookDetailMapper mrBookDetailMapper;

    @Autowired
    private MeetingroomBookChangeMapper mrBookChangeMapper;

    @Override
    public Meetingroom addMeetingroom(MeetingroomVO meetingroomVO) {
        Meetingroom meetingroom = new Meetingroom();
        String roomName = meetingroomVO.getRoomName();
        meetingroom.setRoomName(roomName);

        List<Meetingroom> list = mrMapper.select(meetingroom);
        if (list != null && list.size() > 0)
            throw new MeetingroomException(MeetingroomResultEnum.DUPLICATE_NAME);

        meetingroom.setCapacity(meetingroomVO.getCapacity());
        meetingroom.setStatus("0");
        meetingroom.setRemark(meetingroomVO.getRemark());
        Date date = new Date();
        meetingroom.setCreateTime(date);
        meetingroom.setUpdateTime(date);
        mrMapper.insertUseGeneratedKeys(meetingroom);
        return meetingroom;

    }

    @Override
    public boolean updateMeetingroom(MeetingroomVO meetingroomVO) {

        String roomName = meetingroomVO.getRoomName();

        List<Meetingroom> meetingroomList = getMeetingroomByName(roomName);

        if (meetingroomList == null || meetingroomList.size() == 0)
            throw new MeetingroomException(MeetingroomResultEnum.NOT_EXIST);
        if (meetingroomList.get(0).getId() != meetingroomVO.getId())
            throw new MeetingroomException(MeetingroomResultEnum.DATA_ERROR);
        if (meetingroomList.size() > 1)
            throw new MeetingroomException(MeetingroomResultEnum.DUPLICATE_NAME);

        Meetingroom meetingroom = meetingroomList.get(0);
        meetingroom.setRoomName(meetingroomVO.getRoomName());
        meetingroom.setCapacity(meetingroomVO.getCapacity());
        meetingroom.setStatus(meetingroomVO.getStatus());
        meetingroom.setRemark(meetingroomVO.getRemark());
        meetingroom.setUpdateTime(new Date());
        return mrMapper.updateByPrimaryKey(meetingroom) > 0;

    }

    @Override
    public List<Meetingroom> getMeetingroomByName(String roomName) {
        Meetingroom record = new Meetingroom();
        record.setRoomName(roomName);
        return mrMapper.select(record);
    }

    @Override
    public List<Meetingroom> getMeetingroomById(Integer id) {
        Meetingroom record = new Meetingroom();
        record.setId(id);
        return mrMapper.select(record);
    }

    @Override
    public List<Meetingroom> getValidMeetingroomList() {

        return mrMapper.getValidMeetingroomList();
    }


    @Override
    public boolean cancelMeetingroomById(Integer id, Integer employeeId, String changeReason) {
        mrBookDetailMapper.cancelMeetingroomBookDetailByMeetingroomId(id);
        saveBookChangeForCancelMeetingroom(id, employeeId, changeReason);
        return mrMapper.cancelMeetingroomById(id) > 0;
    }

    @Override
    public List<Meetingroom> getMeetingroomList() {
        return mrMapper.selectAll();
    }

    private void saveBookChangeForCancelMeetingroom(Integer id, Integer employeeId, String changeReason) {

        MeetingroomBookChange record = new MeetingroomBookChange();
        record.setId(null);
        record.setChangeReason(MessageFormat.format("会议室id【{0}】被置为不可用 原因【{1}】", id, changeReason));
        record.setAuditStatus("0");
        record.setEmployeeId(employeeId);
        List<Integer> bookDetailIdList = mrBookDetailMapper.getIdListByMeetingroomId(id);
        if (bookDetailIdList != null && bookDetailIdList.size() > 0) {
            List<MeetingroomBookChange> changelist = new ArrayList<>();
            for (Integer bookDetailId : bookDetailIdList) {
                MeetingroomBookChange bookChange = record.clone();
                bookChange.setMeetingroomBookDetailId(bookDetailId);
                changelist.add(bookChange);
            }
            mrBookChangeMapper.insertList(changelist);
        }
    }

}
