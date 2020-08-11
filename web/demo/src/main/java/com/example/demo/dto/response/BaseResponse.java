package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yuanhang.liu@tcl.com
 * @description BaseResponse
 * @date 2020-06-08 11:05
 **/
@Getter
@AllArgsConstructor
public class BaseResponse {

    private Integer code;
    private String message;
}
