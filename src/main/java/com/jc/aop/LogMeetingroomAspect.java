package com.jc.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.jc.controller.BaseController.getRemoteIp;

/**
 * AOP处理会议室预定系统log
 * 异常信息ErrorController由处理并返回ResultModel
 * Create by onlyLYJ on 2017/10/8
 **/

@Aspect
@Component
@Slf4j
public class LogMeetingroomAspect {

    @Pointcut("execution (* com.jc.controller.Meetingroom*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url
        log.info("url={}", request.getRequestURL());
        //method
        log.info("method={}", request.getMethod());
        //ip
        log.info("ip={}", getRemoteIp(request));
        //class_method
        log.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName());
        //args[]
        log.info("args={}", joinPoint.getArgs());
    }

}
