package com.jc.service.impl;

import com.jc.model.Meetingroom;
import com.jc.service.MeetingroomService;
import org.springframework.stereotype.Service;

@Service
public class MeetingroomServiceImpl implements MeetingroomService {

    @Override
    public Meetingroom addMeetingroom(String roomname, String status, Integer capacity, String remark) {
        return null;
    }

    @Override
    public boolean updateMeetingroom(Meetingroom meetingroom) {
        return false;
    }

    @Override
    public Meetingroom getMeetingroom(String roomname) {
        return null;
    }
}
