package com.jc.controller;

import com.jc.constant.ResultModel;
import com.jc.model.Employee;
import com.jc.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 员工接口
 * Create by onlyLYJ on 2017/9/28
 **/
@Api(description = "员工管理接口")
@Controller
@RequestMapping("/employee")
@Slf4j
public class EmployeeController extends BaseController {

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "增加员工")
    @PostMapping(value = "/add")
    @ResponseBody
    public ResultModel addEmployee(@RequestBody Employee employee) {
        enCodePassword(employee);
        return buildSuccessResponse(employeeService.addEmployee(employee));
    }

    private void enCodePassword(Employee employee) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
    }


}