package com.jc.controller;

import com.jc.service.MeetingroomService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Api(description = "会议室预定接口")
@Controller
@Slf4j
public class MeetingroomController extends BaseController {

    @Autowired
    private MeetingroomService meetingroomService;

}
