package com.example.demo2.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.demo2.service.NacosService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RefreshScope
@RestController
public class NacosController {

    @Value("${userLocalCache:true}")
    private boolean userLocalCache;

    @Resource
    private NacosService nacosService;

    @SentinelResource
    @GetMapping("/nacos/get")
    public String get() {
        return nacosService.getResult(userLocalCache);
    }
}
