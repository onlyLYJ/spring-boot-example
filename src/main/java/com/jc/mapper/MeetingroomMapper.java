package com.jc.mapper;

import com.jc.model.Meetingroom;
import com.jc.util.mybatis.MyMapper;
import org.springframework.stereotype.Component;

@Component
public interface MeetingroomMapper extends MyMapper<Meetingroom> {
    Integer cancelMeetingroomById(Integer id);
}