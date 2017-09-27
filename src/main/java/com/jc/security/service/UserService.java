package com.jc.security.service;

import com.github.pagehelper.PageInfo;
import com.jc.constant.DepartmentEnum;
import com.jc.security.model.Permission;
import com.jc.security.model.Role;
import com.jc.security.model.User;

import java.util.List;

/**
 * Created by jasonzhu on 2017/7/26.
 */
public interface UserService {
    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 根据用户ID查找角色
     *
     * @param userId
     * @return
     */
    List<Role> findRoleByUserId(Integer userId);

    /**
     * 根据用户ID查找权限
     *
     * @param userId
     * @return
     */
    List<Permission> findPermissionByUserId(Integer userId);

    /**
     * 增加员工
     *
     * @param de
     * @param realName
     * @param username
     * @return
     */
    User addUser(DepartmentEnum de, String realName, String username, String password);

    /**
     * 更新员工信息
     *
     * @param User
     * @return
     */
    boolean updateUser(User User);

    /**
     * 分页获得员工
     *
     * @param record
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> selectUser(User record, int pageNum, int pageSize);

    User getUserById(Integer id);

    PageInfo<User> listValidUser(Integer pageNum, Integer pageSize);

}
