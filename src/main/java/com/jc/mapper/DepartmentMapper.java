package com.jc.mapper;

import com.jc.model.Department;
import com.jc.util.mybatis.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DepartmentMapper extends MyMapper<Department> {
    List<Department> getValidDepartmentList();


}