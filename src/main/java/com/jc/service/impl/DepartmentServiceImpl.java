package com.jc.service.impl;

import com.jc.mapper.DepartmentMapper;
import com.jc.model.Department;
import com.jc.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门Service
 * Create by onlyLYJ on 2017/9/28
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final String ENABLE_STATUS = "0";

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> listValidDepartment() {
        return departmentMapper.listValidDepartment();
    }

}
