/*
 *The code is written by 51jiecai.com.
 *All rights reserved.
 */
package com.jc.constant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jc.model.MeetingroomBookDetail;
import com.jc.vo.MeetingroomBookDetailVO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author kenny
 * Created on 2014年11月27日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultModel<T extends Serializable> implements Serializable {

    public static final String RESULT_SUCCESS = "100";//成功
    public static final String RESULT_ERROR = "200";//失败
    public static final String RESULT_TIMES_LIMIT = "201";//交易密码输出次数超过3次

    public static final String RESULT_NOT_MODIFIED = "304";//请求内容未发生变化
    public static final String RESULT_WARN = "300";//警告
    public static final String RESULT_AUTH_INVALID = "400";//登陆无效
    public static final String RESULT_REAL_NAME_INVALID = "401";//未实名认证
    public static final String RESULT_NOPERMISSION = "500";//无权限访问
    public static final String RESULT_NOPERMISSION_LIMIT = "501";//访问频繁
    public static final String RESULT_NOT_SUPPORTED = "600";//不支持，场景/接口不再使用，典型的针对app接口，一段时间废弃后，可以通过返回该响应码
    public static final String RESULT_JIECAI_ERROR = "700";    //推送到今日出错

    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private T data;

    public ResultModel() {

    }

    public ResultModel(String c, String m) {
        this.code = c;
        this.msg = m;
    }

    @JsonProperty("c")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("m")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty("d")
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**
     * 根据List<MeetingroomBookDetailVO>列表构造冲突信息
     *
     * @param conflictList
     * @return
     */
    public static String buildConflictMRBookInfo(List<MeetingroomBookDetailVO> conflictList) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (conflictList == null || conflictList.size() < 0)
            return "";
        String roomName = conflictList.get(0).getRoomName();
        StringBuffer sb = new StringBuffer("冲突会议室: ").append(roomName + "\r");

        for (MeetingroomBookDetail detail : conflictList) {
            sb.append("预定部门： ").append(detail.getDeptName() + "\r");
            sb.append("会议开始时间： ").append(sdf.format(detail.getMeetingBeginTime()) + "\r");
            sb.append("会议结束时间： ").append(sdf.format(detail.getMeetingEndTime()) + "\r\r");
        }
        return sb.toString();
    }


}
