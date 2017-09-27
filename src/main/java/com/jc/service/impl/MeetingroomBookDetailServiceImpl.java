package com.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.jc.exception.MeetingroomBookException;
import com.jc.exception.MeetingroomException;
import com.jc.mapper.MeetingroomBookDetailMapper;
import com.jc.model.MeetingroomBookDetail;
import com.jc.service.MeetingroomBookDetailService;
import com.jc.vo.MeetingroomBookDetailVO;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by onlyLYJ on 2017/9/23
 **/
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class MeetingroomBookDetailServiceImpl implements MeetingroomBookDetailService {

    private static final String VALID_STATUS = "0";

    private static final String INVALID_STATUS = "1";

    private static final String WAIT_AUDIT_STATUS = "0";

    private static final String PASS_AUDIT_STATUS = "1";

    private static final String FAILED_AUDIT_STATUS = "2";

    @Autowired
    private MeetingroomBookDetailMapper meetingroomBookDetailMapper;

    /**
     * 根据会议开始/结束时间修改状态
     *
     * @param meetingroomBookDetail
     */
    public static void setStatus(MeetingroomBookDetail meetingroomBookDetail) {
        if (meetingroomBookDetail == null) return;
        if (INVALID_STATUS.equals(meetingroomBookDetail.getStatus())) {
            meetingroomBookDetail.setStatus("已取消");
            return;
        }
        Date now = new Date();
        if (VALID_STATUS.equals(meetingroomBookDetail.getStatus())) {
            if (meetingroomBookDetail.getMeetingBeginTime().after(now)) {
                meetingroomBookDetail.setStatus("未开始");
                return;
            }
            if (meetingroomBookDetail.getMeetingEndTime().after(now)) {
                meetingroomBookDetail.setStatus("进行中");
                return;
            }
            if (meetingroomBookDetail.getMeetingEndTime().before(now)) {
                meetingroomBookDetail.setStatus("已结束");
                return;
            }
        }
    }

    /**
     * 根据会议开始/结束时间修改状态
     *
     * @param meetingroomBookDetail
     */
    public static void setAuditStatus(MeetingroomBookDetail meetingroomBookDetail) {
        if (meetingroomBookDetail == null) return;
        String auditStatus = meetingroomBookDetail.getAuditStatus();
        switch (auditStatus) {
            case WAIT_AUDIT_STATUS:
                meetingroomBookDetail.setAuditStatus("待审核");
                break;
            case PASS_AUDIT_STATUS:
                meetingroomBookDetail.setAuditStatus("通过");
                break;
            case FAILED_AUDIT_STATUS:
                meetingroomBookDetail.setAuditStatus("未通过");
                break;
            default:
                throw new MeetingroomException("未知审核错误" + auditStatus);
        }
    }

    @Override
    public Integer addMeetingroomBookDetail(MeetingroomBookDetailVO mbdVO) {

        if (!isValidBook(mbdVO)) {
            throw new MeetingroomBookException(MeetingroomBookException.TIME_CONFLICT);
        }

        //TODO 员工id输入需要加入
        if (mbdVO.getUserId() == null)
            mbdVO.setUserId(1);

        mbdVO.setAuditStatus(WAIT_AUDIT_STATUS);
        mbdVO.setStatus(VALID_STATUS);
        Date date = new Date();
        mbdVO.setCreateTime(date);
        mbdVO.setUpdateTime(date);
        return meetingroomBookDetailMapper.insert(mbdVO);

    }

    /**
     * 获取会议室VO获取时间冲突的预定
     *
     * @param mbdVO
     * @return
     */
    @Override
    public List<MeetingroomBookDetailVO> getBookListWithinDatetimeRange(MeetingroomBookDetailVO mbdVO) {

        Date beginTime = mbdVO.getMeetingBeginTime();
        Date endTime = mbdVO.getMeetingEndTime();
        Integer meetingroomId = mbdVO.getMeetingroomId();
        Integer inputId = mbdVO.getId();

        if (beginTime.compareTo(endTime) >= 0)
            throw new MeetingroomBookException(MeetingroomBookException.INVALID_DATA);

        return meetingroomBookDetailMapper.getBookListWithinDatetimeRange(beginTime, endTime, meetingroomId, inputId);
    }

    public Boolean isValidBook(final MeetingroomBookDetailVO mbdVO) {

        List<MeetingroomBookDetailVO> list = getBookListWithinDatetimeRange(mbdVO);

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
            tempVo.setStatus(VALID_STATUS);
            tempVo.setAuditStatus(WAIT_AUDIT_STATUS);

            mbdList.add(tempVo);

            tempBegin = DateUtils.addWeeks(tempBegin, 1);
            tempEnd = DateUtils.addWeeks(tempEnd, 1);
        }

        return meetingroomBookDetailMapper.insertByBatch(mbdList);
    }

    /**
     * @param mbdVO
     * @return
     */
    @Override
    public List<MeetingroomBookDetailVO> updateMeetingroomBookDetailByVO(MeetingroomBookDetailVO mbdVO) {

        //查询时间冲突的预定列表
        List<MeetingroomBookDetailVO> mbdList = getBookListWithinDatetimeRange(mbdVO);

        //冲突数量大于1，即与其他的预定时间冲突，更新失败
        if (mbdList.size() > 1) {
            return mbdList;
        }

        mbdVO.setAuditStatus(WAIT_AUDIT_STATUS);
        mbdVO.setStatus(VALID_STATUS);
        mbdVO.setUpdateTime(new Date());

        Integer num = meetingroomBookDetailMapper.updateByPrimaryKey(mbdVO);

        if (num != 1)
            throw new MeetingroomBookException(MeetingroomBookException.UPDATE_FAILED);

        return Lists.newArrayList(mbdVO);

    }

    @Override
    public List<MeetingroomBookDetail> getMeetingroomBookDetail(MeetingroomBookDetail record) {
        List<MeetingroomBookDetail> mbdList = Lists.newArrayList();
        if (record.getId() > 0) {
            MeetingroomBookDetail meetingroomBookDetail = meetingroomBookDetailMapper.selectByPrimaryKey(record.getId());
            if (meetingroomBookDetail != null) mbdList.add(meetingroomBookDetail);
            return mbdList;
        }
        return meetingroomBookDetailMapper.select(record);
    }

    @Override
    public PageInfo<MeetingroomBookDetail> listMeetingroomBookDetail(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(MeetingroomBookDetail.class);
        example.orderBy("meeting_begin_time").asc();
        List<MeetingroomBookDetail> list = meetingroomBookDetailMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<MeetingroomBookDetail> listValidMeetingroomBookDetail(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MeetingroomBookDetail> list = meetingroomBookDetailMapper.listValidMeetingroomBookDetail();
        return new PageInfo<>(list);
    }

    @Override
    public Integer cancelMeetingroomBookDetailById(Integer id) {

        if (id == null || id <= 0)
            throw new MeetingroomBookException(MeetingroomBookException.INVALID_DATA);

        return meetingroomBookDetailMapper.cancelMeetingroomBookDetailById(id);
    }

    @Override
    public MeetingroomBookDetailVO getMeetingroomBookDetailById(Integer id) {
        return meetingroomBookDetailMapper.selectMeetingroomBookDetailById(id);
    }

    @Override
    public Integer updateAuditStatusById(Integer id, String auditStatus) {

        if (id <= 0 || StringUtils.isEmpty(auditStatus))
            throw new MeetingroomBookException(MeetingroomBookException.INVALID_DATA);

        return meetingroomBookDetailMapper.updateAuditStatusById(id, auditStatus);
    }

}
