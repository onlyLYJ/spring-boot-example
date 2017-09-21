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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    private static final String addSuccess = "会议室增加成功";

    private static final String addFailed = "会议室名：{} 已存在，增加失败";

    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String list(Model model, Integer id) {
        log.info("查询成功!");
        List<Meetingroom> meetingroomList = null;
        if (id != null) {
            Meetingroom record = new Meetingroom();
            record.setId(id);
            meetingroomList = meetingroomService.getMeetingroom(record);
        }
        if (meetingroomList == null || meetingroomList.size() < 1) {
            meetingroomList = meetingroomService.listMeetingroom();
        }

        model.addAttribute("meetingroomList", meetingroomList);
        model.addAttribute("currentTime", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return "listMeetingroom";
    }


    /**
     * @param meetingroomVO
     * @return
     */
    @ApiOperation(value = "增加会议室", notes = "会议室名，可容纳人数是必须输入的")
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public ResultModel addMeetingroom(@ApiParam("会议室增加参数") @Valid @RequestBody MeetingroomVO meetingroomVO) {

        String roomname = meetingroomVO.getRoomname();
        if (meetingroomService.getMeetingroom(roomname) != null) {
            log.error(String.format(addFailed), roomname);
            return buildErrorResponse(addFailed);
        }

        meetingroomService.addMeetingroom(meetingroomVO);
        log.info(addSuccess);
        return buildSuccessResponse(addSuccess);
    }

}
