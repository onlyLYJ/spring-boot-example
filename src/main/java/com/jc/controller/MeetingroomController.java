package com.jc.controller;

import com.jc.constant.MeetingroomResultEnum;
import com.jc.constant.ResultModel;
import com.jc.model.Meetingroom;
import com.jc.service.MeetingroomService;
import com.jc.vo.MeetingroomVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;

/**
 * 会议室管理Controller
 * Create by onlyLYJ on 2017/9/23
 **/


@Api(description = "会议室管理接口")
@Controller
@RequestMapping("/meetingroom")
@Slf4j
public class MeetingroomController extends BaseController {

    @Autowired
    private MeetingroomService meetingroomService;

    @ApiOperation(value = "列出所有会议室", notes = "会议室管理首页", httpMethod = "GET")
    @GetMapping(value = {"", "/list"})
    public String list(Model model, Integer id) {

        List<Meetingroom> meetingroomList = null;
        if (id != null && id > 0) {
            meetingroomList = meetingroomService.getMeetingroomById(id);
        }
        if (meetingroomList == null || meetingroomList.size() < 1) {
            meetingroomList = meetingroomService.getMeetingroomList();
        }

        model.addAttribute("meetingroomList", meetingroomList);
        setCurrentTimeAttribute(model);

        log.info(MeetingroomResultEnum.FIND_MEETINGROOM_LIST_SUCCESS.getMsg());
        return "meetingroomList";
    }

    /**
     * @param meetingroomVO
     * @return
     */
    @ApiOperation(value = "增加会议室", notes = "会议室名，可容纳人数是必须输入的")
    @PostMapping(value = "/add")
    @ResponseBody
    public ResultModel addMeetingroom(@RequestBody @ApiParam("会议室增加参数") @Validated MeetingroomVO meetingroomVO, Principal principal) {

        meetingroomService.addMeetingroom(meetingroomVO);
        return buildResponseByEnum(MeetingroomResultEnum.ADD_SUCCESS);

    }


    @ApiOperation(value = "修改会议室信息", notes = "会议室名，可容纳人数是必须输入的")
    @PostMapping(value = "/update")
    @ResponseBody
    public ResultModel updateMeetingroom(@RequestBody @ApiParam("会议室更新参数") @Validated MeetingroomVO meetingroomVO) {

        if (meetingroomService.updateMeetingroom(meetingroomVO)) {
            log.info(MeetingroomResultEnum.UPDATE_SUCCESS.getMsg());
            return buildResponseByEnum(MeetingroomResultEnum.UPDATE_SUCCESS);
        }

        log.error(MeetingroomResultEnum.UPDATE_FAILED.getMsg());
        return buildResponseByEnum(MeetingroomResultEnum.UPDATE_FAILED);

    }

    @ApiOperation(value = "取消会议室", notes = "将会议室置为不可用")
    @PostMapping(value = "/cancel")
    @ResponseBody
    @Validated
    public ResultModel cancelMeetingroom(@RequestParam @ApiParam("通过id取消会议室") @Min(1) Integer id,
                                         @RequestParam @ApiParam("置位原因") @NotBlank(message = "置位原因不能为空") String changeReason, Principal principal) {

        Integer employeeId = getEmployeeIdByPrincipal(principal);

        if (!meetingroomService.cancelMeetingroomById(id, employeeId, changeReason)) {
            log.error(MeetingroomResultEnum.CANCEL_FAILED.getMsg());
            return buildResponseByEnum(MeetingroomResultEnum.CANCEL_FAILED);
        }

        log.info(MeetingroomResultEnum.CANCEL_SUCCESS.getMsg());
        return buildResponseByEnum(MeetingroomResultEnum.CANCEL_SUCCESS);
    }
}
