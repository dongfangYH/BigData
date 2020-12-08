package com.example.flink.entity;

import lombok.Data;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-08 16:20
 **/
@Data
public class PhoneEvent {
    private Long id;
    private Long timestamp;
    private String phone;
}
