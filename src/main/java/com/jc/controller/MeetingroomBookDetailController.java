package com.jc.controller;

import com.github.pagehelper.PageInfo;
import com.jc.constant.ResultModel;
import com.jc.exception.MeetingroomBookException;
import com.jc.model.Meetingroom;
import com.jc.model.MeetingroomBookDetail;
import com.jc.service.MeetingroomBookDetailService;
import com.jc.service.MeetingroomService;
import com.jc.service.impl.MeetingroomBookDetailServiceImpl;
import com.jc.util.JacksonUtil;
import com.jc.vo.MeetingroomBookDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import java.util.Date;
import java.util.List;

/**
 * 会议室预定详情Controller
 * Create by onlyLYJ on 2017/9/23
 **/

@Api(description = "会议室预定管理接口")
@Controller
@RequestMapping("/meetingroom/book")
@Slf4j
public class MeetingroomBookDetailController extends BaseController {

    private static final String TIME_CONFLICT = "预定时间冲突";
    private static final String APPLY_SUCCESS = "会议预定成功";
    private static final String UPDATE_SUCCESS = "会议修改成功";

    @Autowired
    private MeetingroomBookDetailService meetingroomBookDetailService;

    @Autowired
    private MeetingroomService meetingroomService;

    @ApiOperation(value = "分页显示预定信息", notes = "未过期的")
    @GetMapping(value = {"", "/list"})
    public String allMeetingroomBookDetail(Model model, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageInfo<MeetingroomBookDetail> pageInfo = meetingroomBookDetailService.listValidMeetingroomBookDetail(pageNum, pageSize);
        pageInfo.getList().forEach(MeetingroomBookDetailServiceImpl::setStatus);
        pageInfo.getList().forEach(MeetingroomBookDetailServiceImpl::setAuditStatus);
        model.addAttribute("currentTime", DateFormatUtils.format(new Date(), DATETIME_FORMAT));
        model.addAttribute("page", pageInfo);
        return "listMrBook";
    }

    @ApiOperation(value = "编辑预定")
    @GetMapping(value = {"/edit"})
    public String deleteMeetingroom(Model model, @RequestParam @ApiParam("通过id获取预定") @NonNull @Min(1) Integer id) {

        MeetingroomBookDetail mbd = meetingroomBookDetailService.getMeetingroomBookDetailById(id);

        if (mbd == null) {
            throw new MeetingroomBookException(MeetingroomBookException.INVALID_DATA);
        }

        model.addAttribute("mbd", mbd);
        return "editMrBook";

    }

    @ApiOperation(value = "新增预定")
    @PostMapping(path = "/newBook/add")
    @ResponseBody
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ResultModel addMeetingroomBookDetail(@ModelAttribute(value = "meetingroomBookDetailVO") MeetingroomBookDetailVO meetingroomBookDetailVO) {

        //检查是否是周期预定
        if (isWeeklyBook(meetingroomBookDetailVO)) {

            if (isValidWeeklyBook(meetingroomBookDetailVO)) {
                meetingroomBookDetailService.addWeeklyBook(meetingroomBookDetailVO);
                return buildSuccessResponse(APPLY_SUCCESS);
            }

            return buildErrorResponse(TIME_CONFLICT);
        }

        //非周期预定的情况
        List<MeetingroomBookDetailVO> list = meetingroomBookDetailService.getBookListWithinDatetimeRange(meetingroomBookDetailVO);
        if (list != null && list.size() > 0) {
            return buildErrorResponse(TIME_CONFLICT, JacksonUtil.toJSon(list));
        } else {
            meetingroomBookDetailService.addMeetingroomBookDetail(meetingroomBookDetailVO);
            return buildSuccessResponse(APPLY_SUCCESS);
        }
    }


    @ApiOperation(value = "修改预约")
    @PostMapping(value = "/update")
    @ResponseBody
    public ResultModel updateMeetingroomBookDetail(@Valid MeetingroomBookDetailVO meetingroomBookDetailVO) {

        List<MeetingroomBookDetailVO> list = meetingroomBookDetailService.getBookListWithinDatetimeRange(meetingroomBookDetailVO);

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
    public ResultModel updateMeetingroomBookDetail(@RequestParam @NonNull @Min(1) Integer id, @RequestParam @NotEmpty String auditStatus) {

        Integer num = meetingroomBookDetailService.updateAuditStatusById(id, auditStatus);

        if (num != 1) {
            throw new MeetingroomBookException(MeetingroomBookException.INVALID_DATA);
        }

        return buildSuccessResponse(UPDATE_SUCCESS);
    }

    @ApiOperation(value = "准备model，并跳转到新增预定页面")
    @GetMapping("/newBook")
    public String addMeetingroomBookDetail(Model model) {

        setTimeAttribute(model);
        setRoomlistAttribute(model);
        model.addAttribute("meetingroomBookDetailVO", new MeetingroomBookDetailVO());

        return "addMrBook";
    }

    @ApiOperation(value = "根据id获取预定信息，并跳转到更新页面")
    @GetMapping("/editBook")
    public String updateMeetingroomBookDetail(Model model, @RequestParam @NonNull @Min(1) Integer id) {

        setTimeAttribute(model);
        setRoomlistAttribute(model);

        MeetingroomBookDetail updateMrBook = meetingroomBookDetailService.getMeetingroomBookDetailById(id);
        model.addAttribute("updateMrBook", updateMrBook);
        return "updateMrBook";
    }

    @ApiOperation(value = "审核页面")
    @GetMapping("/audit")
    @ResponseBody
    public MeetingroomBookDetailVO auditMeetingroomBookDetail(@RequestParam @NonNull @Min(1) Integer id) {
        MeetingroomBookDetailVO auditVO = meetingroomBookDetailService.getMeetingroomBookDetailById(id);
        return auditVO;
    }

    @ApiOperation(value = "取消会议预定")
    @PostMapping(value = "/cancel")
    @ResponseBody
    public ResultModel cancelMeetingroomBookDetail(@RequestParam @NonNull @Min(1) Integer id) {

        Integer num = meetingroomBookDetailService.cancelMeetingroomBookDetailById(id);
        if (num != 1) {
            throw new MeetingroomBookException(MeetingroomBookException.INVALID_DATA);
        }

        return buildSuccessResponse(UPDATE_SUCCESS);
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
    private void setTimeAttribute(Model model) {
        model.addAttribute("currentTime", DateFormatUtils.format(new Date(), DATETIME_FORMAT));
    }

    /**
     * 添加会议室列表到model
     *
     * @param model
     */
    private void setRoomlistAttribute(Model model) {
        List<Meetingroom> roomnameList = meetingroomService.listMeetingroom();
        model.addAttribute("roomnameList", roomnameList);
    }

}
