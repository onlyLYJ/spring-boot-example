package com.jc.controller;

import com.github.pagehelper.PageInfo;
import com.jc.constant.BookResultEnum;
import com.jc.constant.ResultModel;
import com.jc.exception.ApplyException;
import com.jc.model.Department;
import com.jc.model.Meetingroom;
import com.jc.model.MeetingroomBookDetail;
import com.jc.service.DepartmentService;
import com.jc.service.MeetingroomBookDetailService;
import com.jc.service.MeetingroomService;
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
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import static com.jc.constant.BookResultEnum.TIME_CONFLICT;
import static com.jc.constant.BookResultEnum.buildConflictMRBookInfo;

/**
 * 会议室预定详情Controller
 * Create by onlyLYJ on 2017/9/23
 **/

@Api(description = "会议室预定管理接口")
@Controller
@RequestMapping("/book")
@Slf4j
public class MeetingroomBookDetailController extends BaseController {

    @Autowired
    private MeetingroomBookDetailService meetingroomBookDetailService;
    @Autowired
    private MeetingroomService meetingroomService;
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "分页显示预定信息", notes = "未过期的")
    @GetMapping(value = {"", "/list"})
    public String allMeetingroomBookDetail(Model model, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Principal principal) {

        PageInfo<MeetingroomBookDetail> pageInfo = meetingroomBookDetailService.getValidMeetingroomBookDetailList(pageNum, pageSize);
        setDeptlistAttribute(model);
        setTimeAndUserAttribute(model, principal);
        model.addAttribute("page", pageInfo);
        return "bookList";
    }

    @ApiOperation(value = "1号会议室极速预定")
    @PostMapping(value = "/speedMeetingroomBook")
    @ResponseBody
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ResultModel speedMeetingroomBookDetail(@RequestParam Integer deptId, @RequestParam Integer minutesAfterNow,
                                                  @RequestParam Integer duration, Principal principal) {

        if (duration == null) {
            throw new ApplyException("预定时长为1-60分钟");
        }

        Integer employeeId = getEmployeeIdByPrincipal(principal);
        if (employeeId == null || employeeId < 1)
            return buildAuthInvalidResponse();

        MeetingroomBookDetailVO mbdVO = buildSpeedMeetingRoomVO(employeeId, deptId, minutesAfterNow, duration);

        List<MeetingroomBookDetailVO> conflictBookList = meetingroomBookDetailService.getConflictBookList(mbdVO);

        if (conflictBookList != null && conflictBookList.size() > 0)
            return buildErrorResponse(TIME_CONFLICT.getCode(), buildConflictMRBookInfo(conflictBookList));

        meetingroomBookDetailService.addMeetingroomBookDetail(mbdVO);
        return buildResponseByEnum(BookResultEnum.APPLY_SUCCESS);

    }

    @ApiOperation(value = "准备model，并跳转到新增预定页面")
    @GetMapping("/newBook")
    public String redirectNewBookPage(Model model) {

        setRoomlistAttribute(model);
        setDeptlistAttribute(model);
        model.addAttribute("meetingroomBookDetailVO", new MeetingroomBookDetailVO());

        return "newBook";
    }


    @ApiOperation(value = "提交新增预定")
    @PostMapping(path = "/newBook/apply")
    @ResponseBody
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ResultModel applyNewBook(@ModelAttribute(value = "meetingroomBookDetailVO") @Valid MeetingroomBookDetailVO meetingroomBookDetailVO, Principal principal) {

        Integer employeeId = getEmployeeIdByPrincipal(principal);
        if (employeeId == null || employeeId < 1)
            return buildAuthInvalidResponse();

        meetingroomBookDetailVO.setEmployeeId(employeeId);
        //检查是否是周期预定
        if (isWeeklyBook(meetingroomBookDetailVO)) {

            if (!isValidWeeklyBook(meetingroomBookDetailVO)) {
                return buildResponseByEnum(BookResultEnum.TIME_CONFLICT);
            }

            meetingroomBookDetailService.addWeeklyBook(meetingroomBookDetailVO);
            return buildResponseByEnum(BookResultEnum.WEEKLY_APPLY_SUCCESS);
        }

        //非周期预定的情况
        List<MeetingroomBookDetailVO> conflictBookList = meetingroomBookDetailService.getConflictBookList(meetingroomBookDetailVO);
        if (conflictBookList != null && conflictBookList.size() > 0) {
            return buildErrorResponse(BookResultEnum.TIME_CONFLICT.getCode(), BookResultEnum.buildConflictMRBookInfo(conflictBookList));
        } else {
            meetingroomBookDetailService.addMeetingroomBookDetail(meetingroomBookDetailVO);
            return buildResponseByEnum(BookResultEnum.APPLY_SUCCESS);
        }

    }


    @ApiOperation(value = "取消会议预定")
    @PostMapping(value = "/cancel")
    @ResponseBody
    public ResultModel cancelMeetingroomBookDetail(@RequestParam @Min(1) Integer id, @RequestParam @NotEmpty String changeReason, Principal principal) {

        Integer employeeId = getEmployeeIdByPrincipal(principal);
        if (employeeId == null || employeeId < 1)
            return buildAuthInvalidResponse();

        Integer updatedNum = meetingroomBookDetailService.cancelMeetingroomBookDetailById(id, employeeId, changeReason);

        if (updatedNum == null || updatedNum < 1) {
            return buildResponseByEnum(BookResultEnum.CANCEL_FAILED);
        }

        return buildResponseByEnum(BookResultEnum.CANCEL_SUCCESS);
    }

    @ApiOperation(value = "修改预约")
    @PostMapping(value = "/update")
    @ResponseBody
    public ResultModel updateMeetingroomBookDetail(@Valid MeetingroomBookDetailVO meetingroomBookDetailVO, Principal principal) {

        Integer employeeId = getEmployeeIdByPrincipal(principal);
        if (employeeId == null || employeeId < 1)
            return buildAuthInvalidResponse();

        meetingroomBookDetailVO.setEmployeeId(employeeId);
        List<MeetingroomBookDetailVO> conflictBookList = meetingroomBookDetailService.getConflictBookList(meetingroomBookDetailVO);

        if (conflictBookList != null && conflictBookList.size() > 0) {
            return buildErrorResponse(BookResultEnum.TIME_CONFLICT.getCode(), BookResultEnum.buildConflictMRBookInfo(conflictBookList));
        } else {
            meetingroomBookDetailService.updateMeetingroomBookDetailByVO(meetingroomBookDetailVO);
            return buildSuccessResponse(BookResultEnum.UPDATE_SUCCESS);
        }

    }

    @ApiOperation(value = "修改审核状态")
    @PostMapping(value = "/editAudit")
    @ResponseBody
    public ResultModel auditMeetingroomBookDetail(@RequestParam @Min(1) Integer id, @RequestParam @NotEmpty String auditStatus, Principal principal) {

        Integer employeeId = getEmployeeIdByPrincipal(principal);
        if (employeeId == null || employeeId < 1)
            return buildAuthInvalidResponse();

        Integer num = meetingroomBookDetailService.updateAuditStatusById(id, auditStatus, employeeId);

        if (num < 1) {
            return buildResponseByEnum(BookResultEnum.EDIT_AUDIT_FAILED);
        }

        return buildResponseByEnum(BookResultEnum.EDIT_AUDIT_SUCCESS);
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

    @ApiOperation(value = "根据id返回信息供审核")
    @GetMapping("/audit")
    @ResponseBody
    public MeetingroomBookDetailVO auditMeetingroomBookDetail(@RequestParam @Min(value = 1, message = "审核的预定不存在") Integer id) {

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
     * 添加当前时间以及用户名到model
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
        List<Meetingroom> roomNameList = meetingroomService.getValidMeetingroomList();
        model.addAttribute("roomNameList", roomNameList);
    }

    /**
     * 添加部门列表到model
     *
     * @param model
     */
    public void setDeptlistAttribute(Model model) {
        List<Department> deptList = departmentService.getValidDepartmentList();
        model.addAttribute("deptList", deptList);
    }

}
