package com.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.jc.aop.LogMrBookChange;
import com.jc.exception.MeetingroomBookException;
import com.jc.mapper.MeetingroomBookChangeMapper;
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
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.Min;
import java.util.ArrayList;
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

    @Autowired
    private MeetingroomBookChangeMapper mrBookChangeMapper;

    @Override
    @LogMrBookChange
    public Integer addMeetingroomBookDetail(MeetingroomBookDetailVO mbdVO) {

        if (!isValidBook(mbdVO)) {
            throw new MeetingroomBookException(MeetingroomBookException.TIME_CONFLICT);
        }

        mbdVO.setAuditStatus("0");
        mbdVO.setStatus("0");
//        Date date = new Date();
//        mbdVO.setUpdateTime(date);
        return mrBookDetailMapper.insertSelective(mbdVO);

    }

    /**
     * 获取会议室VO获取时间冲突的预定
     *
     * @param mbdVO
     * @return
     */
    @Override
    public List<MeetingroomBookDetailVO> findConflictBookList(MeetingroomBookDetailVO mbdVO) {

        Date beginTime = mbdVO.getMeetingBeginTime();
        Date endTime = mbdVO.getMeetingEndTime();
        Integer meetingroomId = mbdVO.getMeetingroomId();
        Integer inputId = mbdVO.getId();

        if (beginTime.compareTo(endTime) >= 0) {
            log.error("开始时间不能晚于结束时间");
            throw new MeetingroomBookException(MeetingroomBookException.INVALID_DATA);
        }

        return mrBookDetailMapper.getBookListWithinDatetimeRange(beginTime, endTime, meetingroomId, inputId);
    }

    public Boolean isValidBook(final MeetingroomBookDetailVO mbdVO) {

        List<MeetingroomBookDetailVO> list = findConflictBookList(mbdVO);

        if (list == null || list.size() == 0)
            return true;

        return false;
    }

    /**
     * 根据vo批量添加周期预定（一周）
     *
     * @param vo
     * @return
     */
    @Override
    public Integer addWeeklyBook(MeetingroomBookDetailVO vo) {
        Date weeklyBookEndDate = vo.getWeeklyBookEndDate();
        Date tempBegin = vo.getMeetingBeginTime();
        Date tempEnd = vo.getMeetingEndTime();

        List<MeetingroomBookDetailVO> mbdList = new ArrayList<>();

        while (tempEnd.compareTo(weeklyBookEndDate) < 0) {
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

        return mrBookDetailMapper.insertByBatch(mbdList);
    }

    /**
     * @param mbdVO
     * @return
     */
    @Override
    public List<MeetingroomBookDetailVO> updateMeetingroomBookDetailByVO(MeetingroomBookDetailVO mbdVO) {

        //查询时间冲突的预定列表
        List<MeetingroomBookDetailVO> mbdList = findConflictBookList(mbdVO);

        //冲突数量大于1，即与其他的预定时间冲突，更新失败
        if (mbdList.size() > 1) {
            return mbdList;
        }

        mbdVO.setStatus("0");
        mbdVO.setAuditStatus("1");
        mbdVO.setUpdateTime(new Date());

        Integer num = mrBookDetailMapper.updateByPrimaryKey(mbdVO);

        if (num != 1)
            throw new MeetingroomBookException(MeetingroomBookException.UPDATE_FAILED);

        return Lists.newArrayList(mbdVO);

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
    public PageInfo<MeetingroomBookDetail> findValidMeetingroomBookDetailList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MeetingroomBookDetail> list = mrBookDetailMapper.listValidMeetingroomBookDetail();
        return new PageInfo<>(list);
    }

    @Override
    @LogMrBookChange
    public Integer cancelMeetingroomBookDetailById(Integer id, @Min(1) Integer employeeId, String changeReason) {
        return mrBookDetailMapper.cancelMeetingroomBookDetailById(id);
    }

    @Override
    public MeetingroomBookDetailVO findMeetingroomBookDetailById(Integer id) {
        return mrBookDetailMapper.selectMeetingroomBookDetailById(id);
    }

    @Override
    public Integer updateAuditStatusById(Integer id, String auditStatus, Integer employeeId) {

        if (id <= 0 || StringUtils.isEmpty(auditStatus))
            throw new MeetingroomBookException(MeetingroomBookException.INVALID_DATA);

        return mrBookDetailMapper.updateAuditStatusById(id, auditStatus);
    }

}
