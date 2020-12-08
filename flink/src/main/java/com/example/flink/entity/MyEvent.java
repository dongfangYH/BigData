package com.example.flink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-08 16:59
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyEvent {

    private Integer eventType;
    private Long timestamp;

}
