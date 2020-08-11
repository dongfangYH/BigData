package com.example.demo.dto.response;

import lombok.Getter;

/**
 * @author yuanhang.liu@tcl.com
 * @description CommonResponse
 * @date 2020-06-08 11:08
 **/
@Getter
public class CommonResponse<T> extends BaseResponse{

    private T data;

    public CommonResponse(Integer code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public CommonResponse(Integer code, String message) {
        super(code, message);
    }


}
