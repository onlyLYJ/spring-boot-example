package com.jc.controller;

import com.jc.constant.BaseEnumBehavior;
import com.jc.constant.ResultModel;
import com.jc.exception.ApplyException;
import com.jc.exception.BaseEnumException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Set;

/**
 * 异常处理
 * Created by jasonzhu on 2017/7/14.
 * Modified by onlyLYJ on 2017/10/05.
 */
@ControllerAdvice
@Controller
@Slf4j
@Aspect
public class ErrorController extends BaseController {

    /**
     * 被@Validated注解的@RequestParam参数验证异常
     * 包装返回ResultModel
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    public ResultModel handleConstraintViolationException(ConstraintViolationException e, Principal principal) {
        log.error("@Validated 参数验证异常 apply by " + principal.getName(), e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        log.error(strBuilder.toString());
        return buildErrorResponse(strBuilder.toString());
    }

    /**
     * 被@Validated注解的VO验证异常
     * 通过BindingResult获取信息，包装返回ResultModel
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {BindException.class})
    @ResponseBody
    public ResultModel handleBindException(BindException e, Principal principal) {
        log.error("@Validated VO验证异常 apply by " + principal.getName(), e);
        BindingResult result = e.getBindingResult();
        return commonValidate(result);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public ResultModel handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Principal principal) {
        log.error("@Validated VO验证异常 apply by " + principal.getName(), e);
        BindingResult result = e.getBindingResult();
        return commonValidate(result);
    }


    /**
     * 业务验证异常
     */
    @ExceptionHandler(value = ApplyException.class)
    @ResponseBody
    public ResultModel authException(ApplyException exception) {
        log.warn("业务验证异常", exception);
        return buildErrorResponse(exception.getMessage());
    }

    /**
     * BaseEnumException异常或其他参数异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultModel handleException(Exception exception, Principal principal) {

        if (exception instanceof BaseEnumException) {
            BaseEnumBehavior enumBehavior = ((BaseEnumException) exception).getEnumBehavior();
            log.warn("Apply by " + principal.getName() + "\r" + enumBehavior.getMsg());
            return buildResponseByEnum(enumBehavior);
        }

        log.error("未预期异常", exception);
        if (exception.getClass() == HttpMessageNotReadableException.class)
            return buildErrorResponse("提交的参数异常，请检查后再提交");
        if (exception.getClass() == SQLException.class)
            return buildErrorResponse("提交的参数不合法，请检查后再提交");
        if (exception.getClass() == IllegalArgumentException.class)
            return buildErrorResponse(exception.getMessage());
        return buildErrorResponse("操作失败，请检查后再重试");
    }

}
