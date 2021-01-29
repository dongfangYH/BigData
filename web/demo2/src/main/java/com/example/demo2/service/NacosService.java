package com.example.demo2.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class NacosService {

    public String getResult(boolean b) {
        if (b)
            return "success!";
        else
            return "fail!";
    }
}
