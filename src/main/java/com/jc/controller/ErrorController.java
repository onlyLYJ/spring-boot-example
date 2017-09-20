package com.jc.controller;

import com.jc.constant.ResultModel;
import com.jc.exception.ApplyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * 异常处理
 * Created by jasonzhu on 2017/7/14.
 */
@ControllerAdvice
@Slf4j
public class ErrorController extends BaseController {
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
     * 未知异常或参数异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultModel exception(Exception exception) {
        log.error("未预期异常", exception);
        if (exception.getClass() == MethodArgumentNotValidException.class || exception.getClass() == HttpMessageNotReadableException.class)
            return buildErrorResponse("提交的参数异常，请检查后再提交");
        if (exception.getClass() == SQLException.class)
            return buildErrorResponse("提交的参数不合法，请检查后再提交");
        if (exception.getClass() == IllegalArgumentException.class)
            return buildErrorResponse(exception.getMessage());
        return buildErrorResponse("操作失败，请检查后再重试");
    }

}
