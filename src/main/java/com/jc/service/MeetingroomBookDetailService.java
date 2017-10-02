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
     * @param MeetingroomBookDetailVO
     * @return
     */
    Integer addMeetingroomBookDetail(MeetingroomBookDetailVO MeetingroomBookDetailVO);

    /**
     * 获得预定时间内的预定信息
     *
     * @param MeetingroomBookDetailVO
     * @return
     */
    List<MeetingroomBookDetailVO> getConflictBookList(MeetingroomBookDetailVO MeetingroomBookDetailVO);

    /**
     * 根据VO更新会议预定详情
     *
     * @param MeetingroomBookDetailVO
     * @return
     */
    List<MeetingroomBookDetailVO> updateMeetingroomBookDetailByVO(MeetingroomBookDetailVO MeetingroomBookDetailVO);


    MeetingroomBookDetailVO findMeetingroomBookDetailById(Integer id);

    /**
     * 根据id号更新审核状态
     *
     * @param id
     * @param auditStatus
     * @param employeeId
     * @return
     */
    Integer updateAuditStatusById(Integer id, String auditStatus, Integer employeeId);

    List<MeetingroomBookDetail> getMeetingroomBookDetail(MeetingroomBookDetail record);

    PageInfo<MeetingroomBookDetail> getAllMeetingroomBookDetail(int pageNum, int pageSize);

    PageInfo<MeetingroomBookDetail> getValidMeetingroomBookDetailList(Integer pageNum, Integer pageSize);

    Integer cancelMeetingroomBookDetailById(Integer id, Integer employeeId, String changeReason);

    Boolean isValidBook(final MeetingroomBookDetailVO mbdVO);

    Integer addWeeklyBook(MeetingroomBookDetailVO MeetingroomBookDetailVO);

}
