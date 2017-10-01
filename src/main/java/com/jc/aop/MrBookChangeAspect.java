package com.jc.aop;

import com.jc.exception.ApplyException;
import com.jc.mapper.MeetingroomBookChangeMapper;
import com.jc.model.MeetingroomBookChange;
import com.jc.vo.MeetingroomBookDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 切面处理
 * Created by jasonzhu on 2017/8/2.
 */
@Aspect
@Component
@Slf4j
public class MrBookChangeAspect {

    @Autowired
    private MeetingroomBookChangeMapper mrBookChangeMapper;

    @Pointcut("@annotation(com.jc.aop.LogMrBookChange)")
    public void annotationPointCut() {
    }

    @Pointcut("@annotation(com.jc.aop.LogMrBookChange)")
    public void annotationPointCutByVO() {
    }

    @After("annotationPointCutByVO() && args(meetingroomBookDetailVO)")
    public void logyMRBookChangeByVO(MeetingroomBookDetailVO meetingroomBookDetailVO) throws Throwable {

        MeetingroomBookChange record = new MeetingroomBookChange();
        Integer id = meetingroomBookDetailVO.getId();
        record.setMeetingroomBookDetailId(id);
        Integer employeeId = meetingroomBookDetailVO.getEmployeeId();
        record.setEmployeeId(employeeId);
        record.setChangeReason(meetingroomBookDetailVO.getBookReason());
        int updatedNum = mrBookChangeMapper.insertSelective(record);

        if (updatedNum < 0) {
            throw new ApplyException(MessageFormat.format("insert meetingroom book change error: bookId[{0}],employeeId[{1}]", id, employeeId));
        }

    }


    @After("annotationPointCut() && args(id, employeeId, changeReason)")
    public void logMRBookChange(Integer id, Integer employeeId, String changeReason) throws Throwable {

        MeetingroomBookChange record = new MeetingroomBookChange();
        record.setMeetingroomBookDetailId(id);
        record.setEmployeeId(employeeId);
        record.setChangeReason(changeReason);
//        record.setCreateTime(new Date());
        int updatedNum = mrBookChangeMapper.insert(record);

        if (updatedNum < 0) {
            throw new ApplyException(MessageFormat.format("insert meetingroom book change error: bookId[{0}],employeeId[{1}]", id, employeeId));
        }

    }

}
