package com.jc.controller;

import com.github.pagehelper.PageInfo;
import com.jc.constant.ResultModel;
import com.jc.model.Department;
import com.jc.model.Meetingroom;
import com.jc.model.MeetingroomBookDetail;
import com.jc.service.DepartmentService;
import com.jc.service.MeetingroomBookDetailService;
import com.jc.service.MeetingroomService;
import com.jc.util.JacksonUtil;
import com.jc.vo.MeetingroomBookDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import static com.jc.constant.ResultModel.buildConflictMRBookInfo;

/**
 * 会议室预定详情Controller
 * Create by onlyLYJ on 2017/9/23
 **/

@Api(description = "会议室预定管理接口")
@Controller
@RequestMapping("/book")
@Slf4j
public class MeetingroomBookDetailController extends BaseController {

    private static final String TIME_CONFLICT = "预定时间冲突";
    private static final String APPLY_SUCCESS = "会议预定成功";
    private static final String UPDATE_SUCCESS = "会议修改成功";

    @Autowired
    private MeetingroomBookDetailService meetingroomBookDetailService;

    @Autowired
    private MeetingroomService meetingroomService;

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "分页显示预定信息", notes = "未过期的")
    @GetMapping(value = {"", "/list"})
    public String allMeetingroomBookDetail(Model model, Integer pageNum, Integer pageSize, Principal principal) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageInfo<MeetingroomBookDetail> pageInfo = meetingroomBookDetailService.findValidMeetingroomBookDetailList(pageNum, pageSize);
        setDeptlistAttribute(model);
        setTimeAndUserAttribute(model, principal);
        model.addAttribute("page", pageInfo);
        return "MRBookList";
    }


    @ApiOperation(value = "1号会议室极速预定")
    @PostMapping(value = "/speedMeetingroomBook")
    @ResponseBody
    public ResultModel speedMeetingroomBookDetail(@RequestParam @Min(1) Integer deptId, @RequestParam @Min(1) @Max(1440) Integer minutesAfterNow, @RequestParam @Min(1) @Max(61) Integer duration, Principal principal) {

        Integer employeeId = getEmployeeIdByPrincipal(principal);

        MeetingroomBookDetailVO mbdVO = buildSpeedMeetingRoomVO(employeeId, deptId, minutesAfterNow, duration);

        List<MeetingroomBookDetailVO> list = meetingroomBookDetailService.findConflictBookList(mbdVO);

        if (list != null && list.size() > 0)
            return buildErrorResponse(buildConflictMRBookInfo(list), "302");

        meetingroomBookDetailService.addMeetingroomBookDetail(mbdVO);
        return buildSuccessResponse(APPLY_SUCCESS);

    }

    @ApiOperation(value = "准备model，并跳转到新增预定页面")
    @GetMapping("/newBook")
    public String redirectNewBookPage(Model model, Principal principal) {

        setRoomlistAttribute(model);
        setDeptlistAttribute(model);
        model.addAttribute("meetingroomBookDetailVO", new MeetingroomBookDetailVO());

        return "addMrBook";
    }


    @ApiOperation(value = "新增预定")
    @PostMapping(path = "/newBook/apply")
    @ResponseBody
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ResultModel redirectNewBookPage(@ModelAttribute(value = "meetingroomBookDetailVO") MeetingroomBookDetailVO meetingroomBookDetailVO, Principal principal) {

        meetingroomBookDetailVO.setEmployeeId(getEmployeeIdByPrincipal(principal));
        //检查是否是周期预定
        if (isWeeklyBook(meetingroomBookDetailVO)) {

            if (isValidWeeklyBook(meetingroomBookDetailVO)) {
                meetingroomBookDetailService.addWeeklyBook(meetingroomBookDetailVO);
                return buildSuccessResponse(APPLY_SUCCESS);
            }
            return buildErrorResponse(TIME_CONFLICT);
        }

        //非周期预定的情况
        List<MeetingroomBookDetailVO> list = meetingroomBookDetailService.findConflictBookList(meetingroomBookDetailVO);
        if (list != null && list.size() > 0) {
            return buildErrorResponse(buildConflictMRBookInfo(list), "302");
        } else {
            meetingroomBookDetailService.addMeetingroomBookDetail(meetingroomBookDetailVO);
            return buildSuccessResponse(APPLY_SUCCESS);
        }

    }


    @ApiOperation(value = "取消会议预定")
    @PostMapping(value = "/cancel")
    @ResponseBody
    public ResultModel cancelMeetingroomBookDetail(@RequestParam @Min(1) Integer id, @RequestParam @NotEmpty String changeReason, @NonNull Principal principal) {

        Integer employeeId = getEmployeeIdByPrincipal(principal);

        Integer updatedNum = meetingroomBookDetailService.cancelMeetingroomBookDetailById(id, employeeId, changeReason);

        if (updatedNum == null || updatedNum < 1) {
            return buildErrorResponse("更新失败");

        }
        return buildSuccessResponse(UPDATE_SUCCESS);
    }

    @ApiOperation(value = "修改预约")
    @PostMapping(value = "/update")
    @ResponseBody
    public ResultModel updateMeetingroomBookDetail(@Valid MeetingroomBookDetailVO meetingroomBookDetailVO) {

        List<MeetingroomBookDetailVO> list = meetingroomBookDetailService.findConflictBookList(meetingroomBookDetailVO);

        if (list != null && list.size() > 0) {
            return buildErrorResponse(TIME_CONFLICT, JacksonUtil.toJSon(list));
        } else {
            meetingroomBookDetailService.updateMeetingroomBookDetailByVO(meetingroomBookDetailVO);
            return buildSuccessResponse(APPLY_SUCCESS);
        }

    }

    @ApiOperation(value = "修改审核状态")
    @PostMapping(value = "/editAudit")
    @ResponseBody
    public ResultModel auditMeetingroomBookDetail(@RequestParam @Min(1) Integer id, @RequestParam @NotEmpty String auditStatus, Principal principal) {

        Integer employeeId = getEmployeeIdByPrincipal(principal);
        Integer num = meetingroomBookDetailService.updateAuditStatusById(id, auditStatus, employeeId);

        if (num < 1) {
            return buildErrorResponse("修改审核失败");
        }

        return buildSuccessResponse(UPDATE_SUCCESS);
    }

    @ApiOperation(value = "根据id获取预定信息，并跳转到更新页面")
    @GetMapping("/editBook")
    public String editMeetingroomBookDetail(Model model, @RequestParam @NonNull @Min(1) Integer id, Principal principal) {

        setTimeAndUserAttribute(model, principal);
        setRoomlistAttribute(model);
        setDeptlistAttribute(model);

        MeetingroomBookDetail updateMrBook = meetingroomBookDetailService.findMeetingroomBookDetailById(id);
        model.addAttribute("updateMrBook", updateMrBook);
        return "updateMrBook";
    }

    @ApiOperation(value = "审核页面")
    @GetMapping("/audit")
    @ResponseBody
    public MeetingroomBookDetailVO auditMeetingroomBookDetail(@RequestParam @NonNull @Min(1) Integer id) {
        MeetingroomBookDetailVO auditVO = meetingroomBookDetailService.findMeetingroomBookDetailById(id);
        return auditVO;
    }

    private MeetingroomBookDetailVO buildSpeedMeetingRoomVO(Integer employeeId, Integer deptId, Integer minutesAfterNow, Integer duration) {
        MeetingroomBookDetailVO vo = new MeetingroomBookDetailVO();
        Date date = DateUtils.addMinutes(new Date(), minutesAfterNow);
        vo.setMeetingBeginTime(date);
        vo.setMeetingEndTime(DateUtils.addMinutes(date, duration));
        vo.setDeptId(deptId);
        vo.setEmployeeId(employeeId);
        vo.setAttendNum(10);
        vo.setMeetingroomId(1);
        vo.setBookReason("极速预定");
        return vo;
    }

    /**
     * 判断是否为周期预定
     *
     * @param vo
     * @return
     */
    private boolean isWeeklyBook(MeetingroomBookDetailVO vo) {
        Date weeklyBookEndDate = vo.getWeeklyBookEndDate();
        if (vo.getIsWeeklyBook() == null || vo.getIsWeeklyBook() == false || weeklyBookEndDate == null)
            return false;
        return true;
    }

    /**
     * 判断周期预定是否冲突
     *
     * @param vo
     * @return
     */
    private boolean isValidWeeklyBook(final MeetingroomBookDetailVO vo) {
        boolean inValid = false;
        Date weeklyBookEndDate = vo.getWeeklyBookEndDate();
        Date meetingBeginTime = vo.getMeetingBeginTime();
        Date meetingEndTime = vo.getMeetingEndTime();

        if (weeklyBookEndDate == null || meetingBeginTime.compareTo(meetingEndTime) > 0 || meetingEndTime.compareTo(weeklyBookEndDate) > 0)
            return inValid;

        MeetingroomBookDetailVO tempVO = new MeetingroomBookDetailVO();
        tempVO.setMeetingroomId(vo.getMeetingroomId());
        Date tempBegin = meetingBeginTime;
        Date tempEnd = meetingEndTime;

        while (tempEnd.compareTo(weeklyBookEndDate) < 0) {
            tempVO.setMeetingBeginTime(tempBegin);
            tempVO.setMeetingEndTime(tempEnd);

            if (!meetingroomBookDetailService.isValidBook(vo))
                return inValid;

            tempBegin = DateUtils.addWeeks(tempBegin, 1);
            tempEnd = DateUtils.addWeeks(tempEnd, 1);
        }

        return !inValid;
    }

    /**
     * 添加当前时间到model
     *
     * @param model
     */
    private void setTimeAndUserAttribute(Model model, Principal principal) {
        model.addAttribute("englishName", principal.getName());
        model.addAttribute("currentTime", DateFormatUtils.format(new Date(), DATETIME_FORMAT));
    }

    /**
     * 添加会议室列表到model
     *
     * @param model
     */
    private void setRoomlistAttribute(Model model) {
        List<Meetingroom> roomNameList = meetingroomService.listMeetingroom();
        model.addAttribute("roomNameList", roomNameList);
    }

    public void setDeptlistAttribute(Model model) {
        List<Department> deptList = departmentService.listValidDepartment();
        model.addAttribute("deptList", deptList);
    }


//
//
//    @ApiOperation(value = "编辑预定")
//    @GetMapping(value = {"/edit"})
//    public String deleteMeetingroom(Model model, @RequestParam @ApiParam("通过id获取预定") @NonNull @Min(1) Integer id) {
//
//        MeetingroomBookDetail mbd = meetingroomBookDetailService.findMeetingroomBookDetailById(id);
//        if (mbd == null) {
//            throw new MeetingroomBookException(MeetingroomBookException.INVALID_DATA);
//        }
//        model.addAttribute("mbd", mbd);
//        return "editMrBook";
//    }


}
