package com.jc.controller;

import com.github.pagehelper.PageInfo;
import com.jc.constant.BookResultEnum;
import com.jc.constant.ResultModel;
import com.jc.model.Department;
import com.jc.model.Meetingroom;
import com.jc.model.MeetingroomBookDetail;
import com.jc.service.DepartmentService;
import com.jc.service.MeetingroomBookDetailService;
import com.jc.service.MeetingroomService;
import com.jc.vo.MeetingroomBookDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.Date;
import java.util.List;

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
    public String allMeetingroomBookDetail(Model model, @RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {

        PageInfo<MeetingroomBookDetail> pageInfo = meetingroomBookDetailService.getValidMeetingroomBookDetailList(pageNum, pageSize);
        setDeptlistAttribute(model);
        setCurrentTimeAttribute(model);
        model.addAttribute("page", pageInfo);
        return "bookList";

    }

    @ApiOperation(value = "1号会议室极速预定")
    @PostMapping(value = "/speedMeetingroomBook")
    @ResponseBody
    @Validated
    public ResultModel speedMeetingroomBookDetail(@RequestParam @ApiParam("部门id") @Min(value = 1, message = "部门信息错误") Integer deptId,
                                                  @RequestParam @ApiParam("minutesAfterNow分钟后会议开始") @Min(value = 1, message = "会议开始时间参数错误") @Max(value = 1440, message = "极速预定不支持1天后的预定") Integer minutesAfterNow,
                                                  @RequestParam @ApiParam("会议持续时间") @Min(value = 1, message = "极速预定会议时长至少为1分钟") @Max(value = 120, message = "极速预定会议时长至多120分钟") Integer duration,
                                                  Principal principal) {

        Integer employeeId = getEmployeeIdByPrincipal(principal);
        if (employeeId == null || employeeId < 1)
            return buildAuthInvalidResponse();

        MeetingroomBookDetailVO mbdVO = buildSpeedMeetingRoomVO(employeeId, deptId, minutesAfterNow, duration);

        if (meetingroomBookDetailService.addMeetingroomBookDetail(mbdVO))
            return buildResponseByEnum(BookResultEnum.APPLY_SUCCESS);

        return buildResponseByEnum(BookResultEnum.APPLY_FAILED);

    }


    @ApiOperation(value = "提交新增预定")
    @PostMapping(path = "/newBook/apply")
    @ResponseBody
    public ResultModel applyNewBook(@ModelAttribute(value = "meetingroomBookDetailVO") @Validated MeetingroomBookDetailVO meetingroomBookDetailVO, Principal principal) {

        if (!setValidEmployeeId(meetingroomBookDetailVO, principal))
            return buildAuthInvalidResponse();

        //检查是否是周期预定
        if (isWeeklyBook(meetingroomBookDetailVO)) {

            if (meetingroomBookDetailService.addWeeklyBook(meetingroomBookDetailVO))
                return buildResponseByEnum(BookResultEnum.WEEKLY_APPLY_SUCCESS);

            return buildResponseByEnum(BookResultEnum.WEEKLY_APPLY_FAILED);
        }

        //非周期预定的情况
        if (meetingroomBookDetailService.addMeetingroomBookDetail(meetingroomBookDetailVO))
            return buildResponseByEnum(BookResultEnum.APPLY_SUCCESS);

        return buildResponseByEnum(BookResultEnum.APPLY_FAILED);

    }

    @ApiOperation(value = "取消会议预定")
    @PostMapping(value = "/cancel")
    @ResponseBody
    @Validated
    public ResultModel cancelMeetingroomBookDetail(@RequestParam @Min(value = 1, message = "取消会议参数错误") Integer id,
                                                   @RequestParam @NotBlank(message = "会议取消原因不能为空") String changeReason, Principal principal) {

        Integer employeeId = getEmployeeIdByPrincipal(principal);
        if (employeeId == null || employeeId < 1)
            return buildAuthInvalidResponse();

        if (meetingroomBookDetailService.cancelMeetingroomBookDetailById(id, employeeId, changeReason))
            return buildResponseByEnum(BookResultEnum.CANCEL_SUCCESS);

        return buildResponseByEnum(BookResultEnum.CANCEL_FAILED);

    }

    @ApiOperation(value = "修改预约")
    @PostMapping(value = "/update")
    @ResponseBody
    public ResultModel updateMeetingroomBookDetail(@Validated MeetingroomBookDetailVO meetingroomBookDetailVO, Principal principal) {

        if (!setValidEmployeeId(meetingroomBookDetailVO, principal))
            return buildAuthInvalidResponse();

        if (meetingroomBookDetailService.updateMeetingroomBookDetailByVO(meetingroomBookDetailVO))
            return buildResponseByEnum(BookResultEnum.UPDATE_SUCCESS);

        return buildResponseByEnum(BookResultEnum.UPDATE_FAILED);

    }

    @ApiOperation(value = "修改审核状态")
    @PostMapping(value = "/editAudit")
    @ResponseBody
    @Validated
    public ResultModel auditMeetingroomBookDetail(@RequestParam @Min(value = 1, message = "审核信息不存在") Integer id, @RequestParam @Pattern(regexp = "^[0|1|2]$", message = "审核状态参数错误") String auditStatus, Principal principal) {

        Integer employeeId = getEmployeeIdByPrincipal(principal);
        if (employeeId == null || employeeId < 1)
            return buildAuthInvalidResponse();

        if (meetingroomBookDetailService.updateAuditStatusById(id, auditStatus, employeeId))
            return buildResponseByEnum(BookResultEnum.EDIT_AUDIT_SUCCESS);

        return buildResponseByEnum(BookResultEnum.EDIT_AUDIT_FAILED);
    }

    @ApiOperation(value = "根据id获取预定信息，并跳转到更新页面")
    @GetMapping("/editBook")
    @Validated
    public String editMeetingroomBookDetail(Model model, @RequestParam @Min(value = 1, message = "需要更新的预定不存在") Integer id) {

        setRoomlistAttribute(model);
        setDeptlistAttribute(model);

        MeetingroomBookDetail updateMrBook = meetingroomBookDetailService.findMeetingroomBookDetailById(id);
        model.addAttribute("updateMrBook", updateMrBook);
        return "updateMrBook";
    }

    @ApiOperation(value = "根据bookDate日期获得预定详情")
    @GetMapping("/findDailyBook")
    public String findDailyBookDetail(Model model, @RequestParam Date bookDate) {

        if (bookDate == null)
            bookDate = new Date();

        List<MeetingroomBookDetail> dailyPage = meetingroomBookDetailService.findDailyBook(bookDate);
        model.addAttribute("dailyBookList", dailyPage);
        return "dailyBook";

    }

    @ApiOperation(value = "准备model，并跳转到新增预定页面")
    @GetMapping("/newBook")
    public String redirectNewBookPage(Model model) {

        setRoomlistAttribute(model);
        setDeptlistAttribute(model);
        MeetingroomBookDetailVO meetingroomBookDetailVO = new MeetingroomBookDetailVO();
        meetingroomBookDetailVO.setAttendNum(10);
        model.addAttribute("meetingroomBookDetailVO", meetingroomBookDetailVO);

        return "newBook";
    }

    @ApiOperation(value = "根据id返回信息供审核")
    @GetMapping("/audit")
    @ResponseBody
    public MeetingroomBookDetailVO auditMeetingroomBookDetail(@RequestParam @Min(value = 1, message = "审核的预定不存在") Integer id) {

        MeetingroomBookDetailVO auditVO = meetingroomBookDetailService.findMeetingroomBookDetailById(id);
        return auditVO;
    }

    /**
     * 1号会议室极速预定默认值：
     * 与会人数10人，会议原因：极速预定
     *
     * @param employeeId
     * @param deptId
     * @param minutesAfterNow
     * @param duration
     * @return
     */
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

    /**
     * 通过Principal获取用户id并绑定到VO
     *
     * @param meetingroomBookDetailVO
     * @param principal
     * @return
     */
    private boolean setValidEmployeeId(MeetingroomBookDetailVO meetingroomBookDetailVO, Principal principal) {
        Integer employeeId = getEmployeeIdByPrincipal(principal);
        if (employeeId == null || employeeId < 1)
            return false;

        meetingroomBookDetailVO.setEmployeeId(employeeId);
        return true;
    }


}
