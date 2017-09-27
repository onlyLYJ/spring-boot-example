package com.jc;

import com.github.pagehelper.PageInfo;
import com.jc.constant.DepartmentEnum;
import com.jc.constant.ResultModel;
import com.jc.model.MeetingroomBookDetail;
import com.jc.security.controller.UserController;
import com.jc.security.service.UserService;
import com.jc.service.ActivityService;
import com.jc.service.ApplyService;
import com.jc.service.MeetingroomBookDetailService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JcApplyApplicationTests {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MeetingroomBookDetailService meetingroomBookDetailService;

    @Test
    public void contextLoads() {
    }

    @Transactional
    @Test
    public void testGetValidMBD() {
        PageInfo<MeetingroomBookDetail> list = meetingroomBookDetailService.listValidMeetingroomBookDetail(1, 10);
        Assert.assertNotNull(list.getList().size());
        System.out.println(list.getList().size());
        System.out.println(list.getList().toString());
    }


    @Test
    @Transactional
    public void testAddEmp() {
//        User user = userService.addUser();
        ResultModel model = userController.addUser(DepartmentEnum.技术部.toString(), "朱圣然", "admin1", "123456");

        System.out.println(model.getCode());
        Assert.assertNotNull(model);
    }

    //	@Test
    public void testRedis() {
        stringRedisTemplate.opsForValue().set("a", "1");
        Assert.assertTrue(stringRedisTemplate.hasKey("a"));
        Assert.assertEquals(stringRedisTemplate.opsForValue().get("a"), "1");
    }

}
