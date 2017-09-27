package com.jc.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jc.constant.DepartmentEnum;
import com.jc.exception.ApplyException;
import com.jc.security.mapper.RolePermissionMapper;
import com.jc.security.mapper.UserMapper;
import com.jc.security.mapper.UserRoleMapper;
import com.jc.security.model.Permission;
import com.jc.security.model.Role;
import com.jc.security.model.User;
import com.jc.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jasonzhu on 2017/7/27.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    private static final String ENABLE_STATUS = "0";
    private static final String UNENABLE_STATUS = "1";

    @Override
    public User addUser(DepartmentEnum de, String realName, String username, String ciperPass) {
        Preconditions.checkNotNull(de, "部门不能为空");
        Preconditions.checkNotNull(realName, "中文名不能为空");
        Preconditions.checkNotNull(username, "用户名不能为空");
        User record = getUserByUsername(username);
        if (record != null)
            throw new ApplyException(MessageFormat.format("用户名已存在 ID【{0}】部门【{1}】中文名【{2}】用户名【{3}】", record.getId(), record.getDepartment(), record.getRealName(), record.getUsername()));
        record = new User();
        record.setDepartment(de.name());
        record.setPassword(ciperPass);
        record.setRealName(realName);
        record.setCreateTime(new Date());
        userMapper.insertUseGeneratedKeys(record);
        return record;
    }

    @Override
    public boolean updateUser(User user) {
        Preconditions.checkNotNull(user, "参数不能为空");
        Preconditions.checkNotNull(user.getId(), "员工ID不能为空");
        return userMapper.updateByPrimaryKeySelective(user) > 0;
    }

    @Override
    public PageInfo<User> selectUser(User record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.select(record);
        return new PageInfo<>(list);
    }


    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<User> listValidUser(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        User example = new User();
        example.setEnable(ENABLE_STATUS);
        List<User> list = userMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public User getUserByUsername(String username) {
        Preconditions.checkNotNull(username, "用户名不能为空");
        User record = new User();
        record.setUsername(username);
        List<User> list = userMapper.select(record);
        return list == null || list.size() < 1 ? null : list.get(0);
    }

    @Override
    public List<Role> findRoleByUserId(Integer userId) {
        Preconditions.checkNotNull(userId, "用户ID不能为空");
        List<Role> list = userRoleMapper.findRoleByUserId(userId);
        return list == null ? Lists.newArrayList() : list;
    }

    @Override
    public List<Permission> findPermissionByUserId(Integer userId) {
        Preconditions.checkNotNull(userId, "用户ID不能为空");
        List<Permission> list = rolePermissionMapper.findPermissionByUserId(userId);
        return list == null ? Lists.newArrayList() : list;
    }


}
