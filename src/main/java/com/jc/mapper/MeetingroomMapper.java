package com.jc.mapper;

import com.jc.model.Meetingroom;
import com.jc.util.mybatis.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MeetingroomMapper extends MyMapper<Meetingroom> {
    int cancelMeetingroomById(Integer id);

    List<Meetingroom> getValidMeetingroomList();
}