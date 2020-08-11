package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-08 16:26
 **/
@Getter
@Setter
public class UserDto {

    private String userName;
    private String email;
    private Integer sex;
}
