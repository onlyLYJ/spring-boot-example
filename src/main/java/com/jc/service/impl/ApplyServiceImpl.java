package com.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jc.exception.ApplyException;
import com.jc.mapper.ActivityApplyMapper;
import com.jc.mapper.ActivityMapper;
import com.jc.mapper.EmployeeMapper;
import com.jc.model.Activity;
import com.jc.model.ActivityApply;
import com.jc.model.Employee;
import com.jc.service.ApplyService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jasonzhu on 2017/7/13.
 */
@Service
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityApplyMapper applyMapper;

    @Override
    public ActivityApply addApply(Integer activityId, Integer employeeId, String remark) throws ApplyException {
        Preconditions.checkNotNull(activityId, "活动ID不能为空");
        Preconditions.checkNotNull(employeeId, "员工ID不能为空");
        ActivityApply record = new ActivityApply();
        record.setActivityId(activityId);
        record.setEmployeeId(employeeId);
        record.setStatus("0");
        List<ActivityApply> applyList = getApply(record);
        if (applyList != null && applyList.size() > 0)
            throw new ApplyException("已报名");
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        if (activity == null) {
            throw new ApplyException("活动不存在");
        }
        Date now = new Date();
        if ("0".equals(activity.getStatus()))
            throw new ApplyException("活动已取消");
        if (activity.getApplyBeginTime().after(now))
            throw new ApplyException(MessageFormat.format("报名未开始 报名开始时间{0}", DateFormatUtils.format(activity.getApplyBeginTime(), "yyyy-MM-dd HH:mm:ss")));
        if (activity.getApplyEndTime().before(now))
            throw new ApplyException(MessageFormat.format("报名已结束 报名结束时间{0}", DateFormatUtils.format(activity.getApplyEndTime(), "yyyy-MM-dd HH:mm:ss")));
        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
        if (employee == null)
            throw new ApplyException("未找到该员工");
        if (activityMapper.addApplyNum(activityId, 1) < 1) {
            throw new ApplyException("报名失败或已过报名时间");
        }
        record.setActivityName(activity.getActivityName());
        record.setRealName(employee.getRealName());
        record.setRemark(remark);
        record.setCreateTime(now);
        applyMapper.insertUseGeneratedKeys(record);

        return record;
    }

    @Override
    public boolean cancelApply(Integer id) {
        Preconditions.checkNotNull(id, "ID不能为空");
        ActivityApply record = applyMapper.selectByPrimaryKey(id);
        if (record == null)
            throw new ApplyException("未找到报名信息");
        if (activityMapper.addApplyNum(record.getActivityId(), -1) < 1) {
            throw new ApplyException("取消失败或已过报名时间");
        }
        record.setId(id);
        record.setStatus("1");
        record.setUpdateTime(new Date());
        return applyMapper.updateByPrimaryKeySelective(record) > 0;
    }

    @Override
    public List<ActivityApply> getApply(ActivityApply record) {
        Preconditions.checkNotNull(record, "参数不能为空");
        if (record.getId() != null) {
            record = applyMapper.selectByPrimaryKey(record.getId());
            if (record != null)
                return Lists.newArrayList(record);
        }
        return applyMapper.select(record);
    }

    @Override
    public PageInfo<ActivityApply> selectApply(ActivityApply record, int pageNum, int pageSize) {
        Preconditions.checkNotNull(record, "参数不能为空");
        PageHelper.startPage(pageNum, pageSize);
        List<ActivityApply> list = applyMapper.select(record);
        return new PageInfo<>(list);
    }
}
