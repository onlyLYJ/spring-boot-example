package com.jc.controller;

import com.jc.constant.ResultModel;
import com.jc.model.Meetingroom;
import com.jc.service.MeetingroomService;
import com.jc.vo.MeetingroomVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Api(description = "会议室预定接口")
@Controller
@RequestMapping("/meetingroom")
@Slf4j
public class MeetingroomController extends BaseController {

    @Autowired
    private MeetingroomService meetingroomService;

    private static final String ADD_SUCCESS = "会议室增加成功";

    private static final String ADD_FAILED = "同名会议室已存在，增加失败";

    private static final String UPDATE_SUCCESS = "同名会议室已存在，增加失败";

    private static final String NOT_EXIST = "会议室不存在";

    private static final String DUPLICATE_NAME = "错误，请删除同名会议室";

    private static final String DELETE_SUCCESS = "会议室删除成功";


    @GetMapping(value = {"", "/list"})
    public String list(Model model, Integer id) {

        List<Meetingroom> meetingroomList = null;
        if (id != null) {
            meetingroomList = meetingroomService.getMeetingroomById(id);
        }
        if (meetingroomList == null || meetingroomList.size() < 1) {
            meetingroomList = meetingroomService.listMeetingroom();
        }

        model.addAttribute("meetingroomList", meetingroomList);
        model.addAttribute("currentTime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("查询会议室列表成功!");
        return "listMeetingroom";
    }


    /**
     * @param meetingroomVO
     * @return
     */
    @ApiOperation(value = "增加会议室", notes = "会议室名，可容纳人数是必须输入的")
    @PostMapping(value = "/add")
    @ResponseBody
    public ResultModel addMeetingroom(@RequestBody @Valid @ApiParam("会议室增加参数") MeetingroomVO meetingroomVO) {

        String roomname = meetingroomVO.getRoomname();
        List<Meetingroom> meetingroomList = meetingroomService.getMeetingroomByName(roomname);
        if (meetingroomList != null && meetingroomList.size() > 0) {
            log.error(ADD_FAILED);
            return buildErrorResponse(ADD_FAILED);
        }
        meetingroomService.addMeetingroom(meetingroomVO);
        log.info(ADD_SUCCESS);
        return buildSuccessResponse(ADD_SUCCESS);
    }


    @ApiOperation(value = "修改会议室信息", notes = "会议室名，可容纳人数是必须输入的")
    @PostMapping(value = "/update")
    @ResponseBody
    public ResultModel updateMeetingroom(@RequestBody @Valid @ApiParam("会议室增加参数") MeetingroomVO meetingroomVO) {

        List<Meetingroom> meetingroomList = meetingroomService.getMeetingroomByName(meetingroomVO.getRoomname());
        if (meetingroomList == null) {
            log.error(NOT_EXIST);
            return buildErrorResponse(NOT_EXIST);
        }
        if (meetingroomList.size() > 1) {
            log.error(DUPLICATE_NAME);
            return buildErrorResponse(DUPLICATE_NAME);
        }
        meetingroomService.updateMeetingroom(meetingroomVO);
        log.info(UPDATE_SUCCESS);
        return buildSuccessResponse(UPDATE_SUCCESS);

    }

    @ApiOperation(value = "删除会议室")
    @PostMapping(value = "/delete")
    @ResponseBody
    public ResultModel deleteMeetingroom(@RequestParam @NotEmpty String roomname) {

        List<Meetingroom> meetingroomList = meetingroomService.getMeetingroomByName(roomname);
        if (meetingroomList == null && meetingroomList.size() > 1) {
            log.error(NOT_EXIST);
            return buildErrorResponse(NOT_EXIST);
        }
        meetingroomService.deleteMeetingroomByName(roomname);
        log.info(DELETE_SUCCESS);
        return buildSuccessResponse(DELETE_SUCCESS);
    }


}
