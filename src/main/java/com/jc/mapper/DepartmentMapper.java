package com.jc.mapper;

import com.jc.model.Department;
import com.jc.util.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper extends MyMapper<Department> {
    List<Department> listValidDepartment();


}