package com.jc.mapper;

import com.jc.model.ActivityApply;
import com.jc.util.mybatis.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ActivityApplyMapper extends MyMapper<ActivityApply> {
    List<ActivityApply> getApplyList(Integer activityId);
}