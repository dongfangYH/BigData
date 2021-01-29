package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.config.BooleanFilter;
import com.example.demo.dto.request.UserDto;
import com.example.demo.dto.response.BaseResponse;
import com.example.demo.dto.Response;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author yuanhang.liu@tcl.com
 * @description UserController
 * @date 2020-06-08 11:01
 **/
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private BooleanFilter booleanFilter;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String REDIS_USER_CACHE_KEY = "user_info:";

    private static final Semaphore semaphore = new Semaphore(10);

    @PostMapping("/user/add")
    public BaseResponse addUser(@RequestBody UserDto userDto) {
        User user = new User();
        BeanCopier copier = BeanCopier.create(UserDto.class, User.class, false);
        copier.copy(userDto, user, null);
        userService.save(user);
        System.out.println(user.getId());
        booleanFilter.addBitMapOffset(user.getId());
        return Response.success();
    }

    @GetMapping("/user/{uid}/info")
    public BaseResponse getUserInfo(@PathVariable Integer uid) {

        boolean exist = booleanFilter.exist(uid);

        if (exist) {
            System.out.println("BOOLEAN 过滤器命中.......");
            ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
            String cacheKey = REDIS_USER_CACHE_KEY + uid;
            String data = valueOps.get(cacheKey);

            User user = null;

            if (null == data) {
                boolean acquire = semaphore.tryAcquire();
                if (acquire) {
                    try {
                        user = userService.getById(uid);
                        valueOps.set(cacheKey, JSON.toJSONString(user), 10, TimeUnit.SECONDS);
                        System.out.println("从数据库获取.......");
                    } finally {
                        semaphore.release();
                    }
                } else {
                    return Response.error();
                }

            } else {
                user = JSON.parseObject(data, User.class);
                System.out.println("从缓存获取.......");
            }

            return Response.success(user);
        }
        System.out.println("BOOLEAN 过滤器未命中........");

        return Response.error();
    }

}
