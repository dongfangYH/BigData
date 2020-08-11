package com.example.demo.dto.response;

/**
 * @author yuanhang.liu@tcl.com
 * @description ErrorResponse
 * @date 2020-06-08 11:09
 **/
public class ErrorResponse extends BaseResponse{

    public ErrorResponse(Integer code, String message) {
        super(code, message);
    }
}
