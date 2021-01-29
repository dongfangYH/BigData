package com.example.demo3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2021-01-18 15:04
 **/
@RestController
public class LogController {

    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/log")
    public String log(){
        log.info("执行任务test完毕");
        log.info("执行任务{}结束，下次执行时间{}", "test", "2020-12-12");
        log.info("短文本");
        return "success.";
    }
}
