package com.jc.controller;

import com.jc.constant.BaseEnumBehavior;
import com.jc.constant.ResultModel;
import com.jc.exception.ApplyException;
import com.jc.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 基础Controller
 */
@Slf4j
@Component
public class BaseController {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    EmployeeService employeeService;

    /**
     * 获得真实ip
     *
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getRemoteHost();
        String realIP = request.getHeader("X-Real-IP");
        if (isNotBlank(realIP)) {
            ip = realIP;
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
        if (ip != null && ip.length() > 15) {
            int index = ip.indexOf(",");
            if (index > -1) {
                ip = ip.substring(0, index);
            }
        }
        return ip;
    }

    Integer getEmployeeIdByPrincipal(Principal principal) {
        String englishName = principal.getName();
        if (StringUtils.isBlank(englishName))
            throw new ApplyException("未登录错误");
        return employeeService.findIdByEnglishName(englishName);
    }

    public <T extends Serializable> ResultModel<T> buildErrorResponse(String msg) {
        ResultModel<T> model = new ResultModel<T>();
        model.setCode(ResultModel.RESULT_ERROR);
        model.setMsg(msg);
        return model;
    }

    protected <T extends Serializable> ResultModel<T> buildErrorResponse(String code, String msg) {
        ResultModel<T> model = new ResultModel<T>();
        model.setCode(code);
        model.setMsg(msg);
        return model;
    }

    protected <T extends Serializable> ResultModel<T> buildErrorResponse(String msg, T obj) {
        ResultModel<T> model = new ResultModel<T>();
        model.setCode(ResultModel.RESULT_ERROR);
        model.setMsg(msg);
        model.setData(obj);
        return model;
    }

    protected <T extends Serializable> ResultModel<T> buildNoPermissionResponse() {
        return buildNoPermissionResponse("没有权限访问");
    }

    protected <T extends Serializable> ResultModel<T> buildNoPermissionResponse(String msg) {
        ResultModel<T> model = new ResultModel<T>();
        model.setCode(ResultModel.RESULT_NOPERMISSION);
        model.setMsg(msg);
        return model;
    }

    protected <T extends Serializable> ResultModel<T> buildLimitResponse() {
        return buildLimitResponse("访问过于频繁，请稍后再试!");
    }

    protected <T extends Serializable> ResultModel<T> buildLimitResponse(String msg) {
        ResultModel<T> model = new ResultModel<T>();
        model.setCode(ResultModel.RESULT_NOPERMISSION_LIMIT);
        model.setMsg(msg);
        return model;
    }

    protected <T extends Serializable> ResultModel<T> buildAuthInvalidResponse() {
        ResultModel<T> model = new ResultModel<T>();
        model.setCode(ResultModel.RESULT_AUTH_INVALID);
        model.setMsg("您的权限验证失败，请登录后再操作！");
        return model;
    }

    protected <T extends Serializable> ResultModel<T> buildJCErrorResponse(String msg, T obj) {
        ResultModel<T> model = new ResultModel<T>();
        model.setCode(ResultModel.RESULT_JIECAI_ERROR);
        model.setMsg(msg);
        model.setData(obj);
        return model;
    }

    protected <T extends Serializable> ResultModel<T> buildSuccessResponse() {
        return buildSuccessResponse(null);
    }

    protected <T extends Serializable> ResultModel<T> buildSuccessResponse(T obj) {
        ResultModel<T> model = new ResultModel<T>();
        model.setCode(ResultModel.RESULT_SUCCESS);
        model.setMsg("成功");
        model.setData(obj);
        return model;
    }

    protected <T extends Serializable> ResultModel<T> buildResponseByEnum(BaseEnumBehavior baseEnumBehavior) {
        ResultModel<T> model = new ResultModel<T>();
        model.setCode(baseEnumBehavior.getCode());
        model.setMsg(baseEnumBehavior.getMsg());
        return model;
    }

    private <T> Map<String, Object> parseDTO(T obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj != null) {
            Set<Field> fset = new HashSet<>();
            fset.addAll(getAllFields(obj.getClass()));
            for (Field f : fset) {
                f.setAccessible(true);
                if (f.getType() == BigDecimal.class) {
                    try {
                        map.put(f.getName(), f.get(obj) != null ? String.valueOf((f.get(obj))) : null);
                    } catch (Exception e) {
                        log.error(f.getName() + "解析失败");
                    }
                } else if (f.getType().getName().endsWith("DTO")) {
                    try {
                        map.put(f.getName(), parseDTO(f.get(obj)));
                    } catch (Exception e) {
                        log.error(f.getName() + "解析失败");
                    }
                } else {
                    try {
                        map.put(f.getName(), f.get(obj));
                    } catch (Exception e) {
                        log.error(f.getName() + "解析失败");
                    }
                }
            }
        }

        return map;
    }


//    public User getUser () throws AuthException {
//        // 校验登录状态
//        User user = Constants.userThreadLocal.get();
//        if (user == null) {
//            throw new AuthException("未登录",AuthException.NOT_LOGIN);
//        }
//        return user;
//    }
//
//    public User getUserByToken(String token) throws AuthException{
//		if (StringUtils.isEmpty(token)){
//			throw new AuthException("未登录",AuthException.NOT_LOGIN);
//		}
//		User u = authService.validateToken(token);
//		if (u==null){
//			throw new AuthException("未登录",AuthException.NOT_LOGIN);
//		}
//		return u;
//	}

    private Set<Field> getAllFields(Class t) {
        Set<Field> fset = new HashSet<>();
        Field[] deClaredFields = t.getDeclaredFields();
        fset.addAll(Arrays.asList(deClaredFields));
        Class c = t.getSuperclass();
        if (c != null)
            fset.addAll(getAllFields(c));
        return fset;
    }

    protected boolean isMobileClient(HttpServletRequest request) {
        boolean flag = false;
        String agent = request.getHeader("user-agent");
        String[] keywords = {"Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser"};
        if (!agent.contains("Windows NT") ||
                (agent.contains("Windows NT") && agent.contains("compatible; MSIE 9.0;"))) {
            if (!agent.contains("Windows NT") && !agent.contains("Macintosh")) {
                for (String item : keywords) {
                    if (agent.contains(item)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 通用框架校验异常处理
     *
     * @param res
     * @return
     */
    ResultModel commonValidate(BindingResult res) {
        if (res.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            res.getFieldErrors().forEach(error -> errorMsg.append("[").append(error.getDefaultMessage()).append("]\r"));
            log.error(errorMsg.toString());
            return buildErrorResponse(errorMsg.toString());
        }
        return null;
    }

    /**
     * 添加当前时间以及用户名到model
     *
     * @param model
     */
    protected void setCurrentTimeAttribute(Model model) {
        model.addAttribute("currentTime", DateFormatUtils.format(new Date(), DATETIME_FORMAT));
    }
}
