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

/**
 * AOP会议室预定更变信息保存
 * Created by jasonzhu on 2017/8/2.
 */
@Aspect
@Component
@Slf4j
public class MrBookChangeAspect {

    @Autowired
    private MeetingroomBookChangeMapper mrBookChangeMapper;

    @Pointcut("@annotation(com.jc.aop.SaveMrBookChange)")
    public void annotationPointCut() {
    }


    @After("annotationPointCut() && args(meetingroomBookDetailVO)")
    public void saveMrBookChangeByVO(MeetingroomBookDetailVO meetingroomBookDetailVO) throws Throwable {

        MeetingroomBookChange record = new MeetingroomBookChange();
        Integer id = meetingroomBookDetailVO.getId();
        record.setMeetingroomBookDetailId(id);
        Integer employeeId = meetingroomBookDetailVO.getEmployeeId();
        record.setEmployeeId(employeeId);
        record.setChangeReason(meetingroomBookDetailVO.getBookReason());
        int updatedNum = mrBookChangeMapper.insertSelective(record);

        if (updatedNum <= 0) {
            throw new ApplyException("变更预定失败");
        }

    }

    @After("annotationPointCut() && args(id, employeeId, changeReason)")
    public void saveMrBookChange(Integer id, Integer employeeId, String changeReason) throws Throwable {

        MeetingroomBookChange record = new MeetingroomBookChange();
        record.setMeetingroomBookDetailId(id);
        record.setEmployeeId(employeeId);
        record.setChangeReason(changeReason);
        int updatedNum = mrBookChangeMapper.insertSelective(record);

        if (updatedNum <= 0) {
            throw new ApplyException("变更预定失败");
        }

    }

}
