package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description UserServiceImpl
 * @date 2020-06-08 10:56
 **/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<Integer> findAllUserId() {
        return userMapper.getAllIds();
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectById(id);
    }
}
