package com.example.demo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@MapperScan("com.example.demo.mapper")
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
public class DemoApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
