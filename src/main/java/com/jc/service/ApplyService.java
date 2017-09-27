package com.jc.service;

import com.github.pagehelper.PageInfo;
import com.jc.exception.ApplyException;
import com.jc.model.ActivityApply;

import java.util.List;

/**
 * Created by jasonzhu on 2017/7/13.
 */
public interface ApplyService {
    /**
     * 新增申请
     *
     * @param activityId
     * @param userId
     * @return
     */
    ActivityApply addApply(Integer activityId, Integer userId, String remark) throws ApplyException;

    /**
     * 取消预约
     *
     * @param activityId
     * @param userId
     * @return
     */
    boolean cancelApply(Integer activityId, Integer userId);

    /**
     * 取消预约
     *
     * @param id
     * @return
     */
    boolean cancelApply(Integer id);

    /**
     * 取消预约
     *
     * @param record
     * @return
     */
    boolean cancelApply(ActivityApply record);

    /**
     * 获得申请
     *
     * @param record
     * @return
     */
    List<ActivityApply> getApply(ActivityApply record);

    List<ActivityApply> getApplyList(Integer activityId);

    /**
     * 分页获得申请
     *
     * @param record
     * @return
     */
    PageInfo<ActivityApply> selectApply(ActivityApply record, int pageNum, int pageSize);

}
