package com.jc.service;

import com.github.pagehelper.PageInfo;
import com.jc.model.MeetingroomBookDetail;
import com.jc.vo.MeetingroomBookDetailVO;

import java.util.List;

/**
 * Create by onlyLYJ on 2017/9/23
 **/

public interface MeetingroomBookDetailService {

    /**
     * 增加会议预定
     *
     * @param meetingroomBookDetailVO
     * @return
     */
    Integer addMeetingroomBookDetail(MeetingroomBookDetailVO meetingroomBookDetailVO);

    /**
     * 获得预定时间内的预定信息
     *
     * @param meetingroomBookDetailVO
     * @return
     */
    List<MeetingroomBookDetailVO> getBookListWithinDatetimeRange(MeetingroomBookDetailVO meetingroomBookDetailVO);

    /**
     * 根据VO更新会议预定详情
     *
     * @param meetingroomBookDetailVO
     * @return
     */
    List<MeetingroomBookDetailVO> updateMeetingroomBookDetailByVO(MeetingroomBookDetailVO meetingroomBookDetailVO);


    MeetingroomBookDetailVO getMeetingroomBookDetailById(Integer id);

    /**
     * 根据id号更新审核状态
     *
     * @param id
     * @param auditStatus
     * @return
     */
    Integer updateAuditStatusById(Integer id, String auditStatus);

    List<MeetingroomBookDetail> getMeetingroomBookDetail(MeetingroomBookDetail record);

    PageInfo<MeetingroomBookDetail> listMeetingroomBookDetail(int pageNum, int pageSize);

    PageInfo<MeetingroomBookDetail> listValidMeetingroomBookDetail(Integer pageNum, Integer pageSize);

    Integer cancelMeetingroomBookDetailById(Integer id);

    Boolean isValidBook(final MeetingroomBookDetailVO mbdVO);

    Integer addWeeklyBook(MeetingroomBookDetailVO meetingroomBookDetailVO);
}
