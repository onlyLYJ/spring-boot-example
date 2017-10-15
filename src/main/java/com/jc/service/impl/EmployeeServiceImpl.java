package com.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jc.constant.DepartmentEnum;
import com.jc.exception.ApplyException;
import com.jc.mapper.EmployeeMapper;
import com.jc.model.Employee;
import com.jc.security.mapper.RolePermissionMapper;
import com.jc.security.mapper.UserRoleMapper;
import com.jc.security.model.Permission;
import com.jc.security.model.Role;
import com.jc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by onlyLYJ on 2017/7/13.
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Employee addEmployee(DepartmentEnum de, String realName, String englishName, String password) {
        Preconditions.checkNotNull(de, "部门不能为空");
        Preconditions.checkNotNull(realName, "真是姓名不能为空");
        Preconditions.checkNotNull(englishName, "英文名不能为空");
        Employee record = findByEnglishName(englishName);
        if (record != null) {
            throw new ApplyException(MessageFormat.format("英文名已存在 ID【{0}】部门【{1}】姓名【{2}】英文名【{3}】", record.getId(), record.getDepartment(), record.getRealName(), record.getEnglishName()));
        }
        record = new Employee();
        record.setDepartment(de.name());
        record.setPassword(password);
        record.setRealName(realName);
        record.setEnglishName(englishName);
        record.setCreateTime(new Date());
        record.setEnable("0");
        employeeMapper.insertUseGeneratedKeys(record);
        return record;
    }

    @Override
    public boolean cancelEmployeeById(Integer id) {

        Employee record = employeeMapper.selectByPrimaryKey(id);
        if (record == null) {
            throw new ApplyException(MessageFormat.format("用户不存在 ID【{0}】部门【{1}】姓名【{2}】英文名【{3}】", record.getId(), record.getDepartment(), record.getRealName(), record.getEnglishName()));
        }
        record.setUpdateTime(new Date());
        record.setEnable("1");
        return employeeMapper.updateByPrimaryKey(record) > 0;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        Preconditions.checkNotNull(employee, "参数不能为空");
        Preconditions.checkNotNull(employee.getId(), "员工ID不能为空");
        employee.setUpdateTime(new Date());
        return employeeMapper.updateByPrimaryKeySelective(employee) > 0;
    }

    @Override
    public PageInfo<Employee> selectEmployee(Employee record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employee> list = employeeMapper.select(record);

        for (Employee employee : list) {
            employee.setRoles(findRoleByEmployeeId(employee.getId()));
        }

        return new PageInfo<>(list);
    }


    @Override
    public Employee findByEnglishName(String englishName) {
        Preconditions.checkNotNull(englishName, "用户名不能为空");
        Employee record = new Employee();
        record.setEnglishName(englishName);
        List<Employee> list = employeeMapper.select(record);
        return list == null || list.size() < 1 ? null : list.get(0);
    }

    @Override
    public Integer findIdByEnglishName(String englishName) {
        Preconditions.checkNotNull(englishName, "用户名不能为空");
        List<Integer> list = employeeMapper.findIdByEnglishName(englishName);
        return list == null || list.size() < 1 ? null : list.get(0);
    }


    @Override
    public List<Permission> findPermissionByEmployeeId(Integer employeeId) {
        Preconditions.checkNotNull(employeeId, "用户ID不能为空");
        List<Permission> list = rolePermissionMapper.findPermissionByUserId(employeeId);
        return list == null ? Lists.newArrayList() : list;
    }


    @Override
    public List<Role> findRoleByEmployeeId(Integer employeeId) {
        Preconditions.checkNotNull(employeeId, "用户ID不能为空");
        List<Role> list = userRoleMapper.findRoleByUserId(employeeId);
        return list == null ? Lists.newArrayList() : list;
    }

    @Override
    public boolean addEmployee(Employee employee) {
        Employee record = findByEnglishName(employee.getEnglishName());
        if (record != null) {
            throw new ApplyException(MessageFormat.format("英文名已存在 ID【{0}】部门【{1}】姓名【{2}】英文名【{3}】", record.getId(), record.getDepartment(), record.getRealName(), record.getEnglishName()));
        }
        employee.setCreateTime(new Date());
        employee.setEnable("0");
        return employeeMapper.insertUseGeneratedKeys(employee) > 0;

    }


}
