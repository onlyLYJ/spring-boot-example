package com.jc.mapper;

import com.jc.model.MeetingroomBookDetail;
import com.jc.util.mybatis.MyMapper;
import com.jc.vo.MeetingroomBookDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface MeetingroomBookDetailMapper extends MyMapper<MeetingroomBookDetail> {

    List<MeetingroomBookDetailVO> getConflictBookList(@Param(value = "meetingBeginTime") Date meetingBeginTime, @Param(value = "meetingEndTime") Date meetingEndTime, @Param(value = "meetingroomId") Integer meetingroomId, @Param(value = "inputId") Integer inputId);

    int updateAuditStatusById(@Param(value = "id") Integer id, @Param(value = "auditStatus") String auditStatus);

    List<MeetingroomBookDetail> getValidMeetingroomBookDetailList();

    int cancelMeetingroomBookDetailById(Integer id);

    boolean cancelMeetingroomBookDetailByMeetingroomId(Integer meetingroomId);

    int insertByBatch(List<MeetingroomBookDetailVO> mbdList);

    MeetingroomBookDetailVO selectMeetingroomBookDetailById(Integer id);

    List<Integer> getIdListByMeetingroomId(Integer id);

    List<MeetingroomBookDetail> findDailyBookByRoomId(
            @Param(value = "beginTime") Date beginTime,
            @Param(value = "endTime") Date endTime);
}