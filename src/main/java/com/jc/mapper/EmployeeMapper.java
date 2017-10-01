package com.jc.mapper;

import com.jc.model.Employee;
import com.jc.util.mybatis.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmployeeMapper extends MyMapper<Employee> {
    List<Integer> findIdByEnglishName(String englishName);
}