package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yuanhang.liu@tcl.com
 * @description User Entity
 * @date 2020-06-08 10:52
 **/
@Data
@TableName(value = "t_user")
public class User extends BaseModel<User> {

    private String userName;
    private Integer sex;
    private String email;

}
