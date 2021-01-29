package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-08 12:27
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class TestUser {

    @Resource
    private UserService userService;

    @Test
    public void addUser() {
        User user = new User();
        user.setUserName("xiaoming");
        user.setSex(1);
        user.setEmail("xiaoming@163.com");
        userService.save(user);
        System.out.println(user.getId());
    }
}
