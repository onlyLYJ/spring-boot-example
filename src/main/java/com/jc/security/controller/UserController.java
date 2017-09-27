package com.jc.security.controller;

import com.github.pagehelper.PageInfo;
import com.jc.aop.SerializedField;
import com.jc.constant.DepartmentEnum;
import com.jc.constant.ResultModel;
import com.jc.controller.BaseController;
import com.jc.security.model.User;
import com.jc.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;

/**
 * 用户管理接口
 * Create onlyLYJ on 2017/9/27
 **/

@Api(description = "用户管理接口")
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController {

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    /**
     * 增加用户
     *
     * @param department 部门
     * @param realName   姓名
     * @param username   用户名
     * @return
     */
    @ApiOperation(value = "增加用户")
    @PostMapping(value = "/addUser")
    public ResultModel addUser(@RequestParam @NotEmpty String department, @RequestParam @NotEmpty String realName, @RequestParam @NotEmpty String username, @RequestParam @NotEmpty String password) {

        userService.addUser(DepartmentEnum.match(department), realName, username, encoder.encode(password));
        return buildSuccessResponse();
    }

    @ApiOperation(value = "根据查询用户")
    @GetMapping
    @SerializedField(excludes = {"password"}, encode = false)
    public User getUserById(@RequestParam @ApiParam("通过id获取用户信息") @NonNull @Min(1) Integer id) {
        return userService.getUserById(id);
    }


    @ApiOperation(value = "查询用户")
    @GetMapping(value = {"", "/list"})
    @SerializedField(excludes = {"password"})
    public String listAllUser(Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageInfo<User> pageInfo = userService.listValidUser(pageNum, pageSize);
        return "listUser";
    }


}
