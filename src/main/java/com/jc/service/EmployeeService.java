package com.jc.service;

import com.github.pagehelper.PageInfo;
import com.jc.constant.DepartmentEnum;
import com.jc.model.Employee;
import com.jc.security.model.Permission;
import com.jc.security.model.Role;

import java.util.List;

/**
 * Created by jasonzhu on 2017/7/13.
 */
public interface EmployeeService {
    /**
     * 增加员工
     *
     * @param de
     * @param realName
     * @param englishName
     * @return
     */
    Employee addEmployee(DepartmentEnum de, String realName, String englishName, String password);

    /**
     * 根据id禁用员工
     *
     * @param id
     * @return
     */
    boolean cancelEmployeeById(Integer id);

    /**
     * 更新员工信息
     *
     * @param employee
     * @return
     */
    boolean updateEmployee(Employee employee);

    /**
     * 分页获得员工
     *
     * @param record
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Employee> selectEmployee(Employee record, int pageNum, int pageSize);

    /**
     * 根据英文名获得员工
     *
     * @param englishName
     * @return
     */
    Employee findByEnglishName(String englishName);

    /**
     * 根据英文名获取员工id
     *
     * @param englishName
     * @return
     */
    Integer findIdByEnglishName(String englishName);

    List<Permission> findPermissionByEmployeeId(Integer id);

    List<Role> findRoleByEmployeeId(Integer employeeId);

    boolean addEmployee(Employee employee);

}
