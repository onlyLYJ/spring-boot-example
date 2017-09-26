package com.jc.mapper;

import com.jc.model.MeetingroomBookDetail;
import com.jc.util.mybatis.MyMapper;
import com.jc.vo.MeetingroomBookDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface MeetingroomBookDetailMapper extends MyMapper<MeetingroomBookDetail> {

    List<MeetingroomBookDetailVO> getBookListWithinDatetimeRange(@Param(value = "meetingBeginTime") Date meetingBeginTime, @Param(value = "meetingEndTime") Date meetingEndTime, @Param(value = "meetingroomId") Integer meetingroomId, @Param(value = "inputId") Integer inputId);

    Integer updateMeetingroomBookDetailByVO(MeetingroomBookDetailVO mbdVO);

    Integer updateAuditStatusById(@Param(value = "id") Integer id, @Param(value = "auditStatus") String auditStatus);

    List<MeetingroomBookDetail> listValidMeetingroomBookDetail();

    Integer cancelMeetingroomBookDetailById(Integer id);

    Integer cancelMeetingroomBookDetailByMeetingroomId(Integer meetingroomId);

    Integer insertByBatch(List<MeetingroomBookDetailVO> mbdList);

    MeetingroomBookDetailVO selectMeetingroomBookDetailById(Integer id);

}