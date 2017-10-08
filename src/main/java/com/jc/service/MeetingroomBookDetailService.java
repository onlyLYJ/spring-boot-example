package com.jc.service;

import com.github.pagehelper.PageInfo;
import com.jc.model.MeetingroomBookDetail;
import com.jc.vo.MeetingroomBookDetailVO;

import java.util.Date;
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
    boolean addMeetingroomBookDetail(MeetingroomBookDetailVO MeetingroomBookDetailVO);

    /**
     * 获得预定时间内的预定信息
     *
     * @param MeetingroomBookDetailVO
     * @return
     */
    List<MeetingroomBookDetailVO> getConflictBookList(MeetingroomBookDetailVO MeetingroomBookDetailVO);

    List<MeetingroomBookDetail> findDailyBook(Date bookDate);

    /**
     * 根据VO更新会议预定详情
     *
     * @param MeetingroomBookDetailVO
     * @return
     */
    boolean updateMeetingroomBookDetailByVO(MeetingroomBookDetailVO MeetingroomBookDetailVO);


    MeetingroomBookDetailVO findMeetingroomBookDetailById(Integer id);

    /**
     * 根据id号更新审核状态
     *
     * @param id
     * @param auditStatus
     * @param employeeId
     * @return
     */
    boolean updateAuditStatusById(Integer id, String auditStatus, Integer employeeId);

    List<MeetingroomBookDetail> getMeetingroomBookDetail(MeetingroomBookDetail record);

    PageInfo<MeetingroomBookDetail> getAllMeetingroomBookDetail(int pageNum, int pageSize);

    PageInfo<MeetingroomBookDetail> getValidMeetingroomBookDetailList(Integer pageNum, Integer pageSize);

    boolean cancelMeetingroomBookDetailById(Integer id, Integer employeeId, String changeReason);

    boolean addWeeklyBook(MeetingroomBookDetailVO MeetingroomBookDetailVO);

}
