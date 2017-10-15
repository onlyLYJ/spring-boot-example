package com.jc.controller;

import com.github.pagehelper.PageInfo;
import com.jc.constant.BookResultEnum;
import com.jc.constant.ResultModel;
import com.jc.model.Employee;
import com.jc.security.model.Role;
import com.jc.service.EmployeeService;
import com.jc.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * 员工接口
 * Create by onlyLYJ on 2017/10/09
 **/
@Api(description = "员工管理接口")
@Controller
@RequestMapping("/employee")
@Slf4j
public class EmployeeController extends BaseController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "增加员工")
    @PostMapping(value = "/add")
    @ResponseBody
    public ResultModel addEmployee(@RequestBody @Validated Employee employee) {
        enCodePassword(employee);
        if (employeeService.addEmployee(employee)) {
            return buildSuccessResponse("用户注册成功!");
        }
        return buildErrorResponse("注册失败，请稍后再试");
    }

    private void enCodePassword(Employee employee) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
    }

    @ApiOperation(value = "员工管理页面首页", notes = "列举所有enable=0 即可用员工")
    @GetMapping(value = {"", "/index", "/list", "/"})
    public String index(Model model, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;

        Employee employee = new Employee();
        employee.setEnable("0");
        PageInfo<Employee> employeeList = employeeService.selectEmployee(employee, pageNum, pageSize);
        List<Role> roleList = roleService.getRole();

        setCurrentTimeAttribute(model);
        model.addAttribute("employeeList", employeeList);
        model.addAttribute("roleList", roleList);
        return "employeeList";
    }

    @ApiOperation(value = "禁用用户")
    @PostMapping(value = "/cancel")
    @ResponseBody
    @Validated
    public ResultModel cancelEmployee(@RequestParam @Min(value = 1, message = "员工参数错误") Integer id) {

        if (employeeService.cancelEmployeeById(id)) {
            return buildResponseByEnum(BookResultEnum.CANCEL_SUCCESS);
        }

        return buildResponseByEnum(BookResultEnum.CANCEL_FAILED);

    }


}
