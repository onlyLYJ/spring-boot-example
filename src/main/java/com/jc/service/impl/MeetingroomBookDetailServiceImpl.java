package com.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.jc.aop.SaveMrBookChange;
import com.jc.constant.BookResultEnum;
import com.jc.exception.BaseEnumException;
import com.jc.exception.MeetingroomBookException;
import com.jc.mapper.MeetingroomBookDetailMapper;
import com.jc.model.MeetingroomBookDetail;
import com.jc.service.MeetingroomBookDetailService;
import com.jc.vo.MeetingroomBookDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Create by onlyLYJ on 2017/9/23
 **/
@Service
@Slf4j
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class MeetingroomBookDetailServiceImpl implements MeetingroomBookDetailService {

    @Autowired
    private MeetingroomBookDetailMapper mrBookDetailMapper;

    @Override
    public boolean addMeetingroomBookDetail(MeetingroomBookDetailVO mbdVO) {

        List<MeetingroomBookDetailVO> conflictBookList = getConflictBookList(mbdVO);

        if (conflictBookList != null && conflictBookList.size() > 0)
            throw new MeetingroomBookException(conflictBookList);

        mbdVO.setAuditStatus("0");
        mbdVO.setStatus("0");
        return mrBookDetailMapper.insertSelective(mbdVO) > 0;

    }

    /**
     * 通过会议室VO获取时间冲突的预定
     *
     * @param mbdVO
     * @return
     */
    @Override
    public List<MeetingroomBookDetailVO> getConflictBookList(MeetingroomBookDetailVO mbdVO) {

        Date beginTime = mbdVO.getMeetingBeginTime();
        Date endTime = mbdVO.getMeetingEndTime();

        if (beginTime.after(endTime))
            throw new MeetingroomBookException(BookResultEnum.INVALID_DATA);

        Integer meetingroomId = mbdVO.getMeetingroomId();
        Integer inputId = mbdVO.getId();

        return mrBookDetailMapper.getConflictBookList(beginTime, endTime, meetingroomId, inputId);
    }

    /**
     * 根据vo批量添加周期预定（一周）
     *
     * @param vo
     * @return
     */
    @Override
    public boolean addWeeklyBook(MeetingroomBookDetailVO vo) {

        List<MeetingroomBookDetailVO> weeklyConflictList = getWeeklyConflictList(vo);

        if (weeklyConflictList.size() > 0)
            throw new MeetingroomBookException(weeklyConflictList);

        Date weeklyBookEndDate = vo.getWeeklyBookEndDate();
        Date tempBegin = vo.getMeetingBeginTime();
        Date tempEnd = vo.getMeetingEndTime();

        List<MeetingroomBookDetailVO> mbdList = new ArrayList<>();

        while (tempEnd.before(weeklyBookEndDate)) {

            MeetingroomBookDetailVO tempVo = vo.clone();
            tempVo.setId(null);
            tempVo.setMeetingBeginTime(tempBegin);
            tempVo.setMeetingEndTime(tempEnd);
            tempVo.setStatus("0");
            tempVo.setAuditStatus("0");

            mbdList.add(tempVo);

            tempBegin = DateUtils.addWeeks(tempBegin, 1);
            tempEnd = DateUtils.addWeeks(tempEnd, 1);
        }

        return mrBookDetailMapper.insertByBatch(mbdList) == mbdList.size();
    }

    /**
     * 判断周期预定是否冲突
     *
     * @param vo
     * @return
     */
    private List<MeetingroomBookDetailVO> getWeeklyConflictList(final MeetingroomBookDetailVO vo) {

        List<MeetingroomBookDetailVO> conflictList = new ArrayList<>();

        Date meetingBeginTime = vo.getMeetingBeginTime();
        Date meetingEndTime = vo.getMeetingEndTime();
        Date weeklyBookEndDate = vo.getWeeklyBookEndDate();

        if (meetingBeginTime.after(meetingEndTime) || meetingEndTime.after(weeklyBookEndDate))
            throw new BaseEnumException(BookResultEnum.INVALID_DATA);

        MeetingroomBookDetailVO tempVO = new MeetingroomBookDetailVO();
        tempVO.setMeetingroomId(vo.getMeetingroomId());
        Date tempBegin = meetingBeginTime;
        Date tempEnd = meetingEndTime;

        while (tempEnd.before(weeklyBookEndDate)) {
            tempVO.setMeetingBeginTime(tempBegin);
            tempVO.setMeetingEndTime(tempEnd);

            conflictList.addAll(getConflictBookList(tempVO));

            tempBegin = DateUtils.addWeeks(tempBegin, 1);
            tempEnd = DateUtils.addWeeks(tempEnd, 1);
        }

        return conflictList;
    }


    @Override
    public List<MeetingroomBookDetail> findDailyBook(Date bookDate) {

        Date endTime = DateUtils.ceiling(bookDate, Calendar.DATE);

        return mrBookDetailMapper.findDailyBookByRoomId(bookDate, endTime);
    }

    /**
     * @param mbdVO
     * @return
     */
    @Override
    @SaveMrBookChange
    public boolean updateMeetingroomBookDetailByVO(MeetingroomBookDetailVO mbdVO) {

        List<MeetingroomBookDetailVO> conflictBookList = getConflictBookList(mbdVO);
        if (conflictBookList != null && conflictBookList.size() > 0)
            throw new MeetingroomBookException(conflictBookList);

        mbdVO.setStatus("0");
        mbdVO.setAuditStatus("0");
        mbdVO.setUpdateTime(new Date());
        return mrBookDetailMapper.updateByPrimaryKey(mbdVO) > 0;
    }

    @Override
    public List<MeetingroomBookDetail> getMeetingroomBookDetail(MeetingroomBookDetail record) {
        List<MeetingroomBookDetail> mbdList = Lists.newArrayList();
        if (record.getId() > 0) {
            MeetingroomBookDetail meetingroomBookDetail = mrBookDetailMapper.selectByPrimaryKey(record.getId());
            if (meetingroomBookDetail != null) mbdList.add(meetingroomBookDetail);
            return mbdList;
        }
        return mrBookDetailMapper.select(record);
    }

    @Override
    public PageInfo<MeetingroomBookDetail> getAllMeetingroomBookDetail(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(MeetingroomBookDetail.class);
        example.orderBy("meeting_begin_time").asc();
        List<MeetingroomBookDetail> list = mrBookDetailMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<MeetingroomBookDetail> getValidMeetingroomBookDetailList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MeetingroomBookDetail> list = mrBookDetailMapper.getValidMeetingroomBookDetailList();
        return new PageInfo<>(list);
    }

    @Override
    @SaveMrBookChange
    public boolean cancelMeetingroomBookDetailById(Integer id, Integer employeeId, String changeReason) {

        return mrBookDetailMapper.cancelMeetingroomBookDetailById(id) > 0;

    }

    @Override
    public MeetingroomBookDetailVO findMeetingroomBookDetailById(Integer id) {

        return mrBookDetailMapper.selectMeetingroomBookDetailById(id);

    }

    @Override
    public boolean updateAuditStatusById(Integer id, String auditStatus, Integer employeeId) {

        return mrBookDetailMapper.updateAuditStatusById(id, auditStatus) > 0;

    }

}
