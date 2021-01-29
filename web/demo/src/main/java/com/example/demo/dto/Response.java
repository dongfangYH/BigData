package com.example.demo.dto;

import com.example.demo.dto.response.BaseResponse;
import com.example.demo.dto.response.CommonResponse;
import com.example.demo.dto.response.ErrorResponse;

import static com.example.demo.exception.ResponseEnum.RESOURCE_NOT_FOUND;
import static com.example.demo.exception.ResponseEnum.SUCCESS;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-08 11:29
 **/
public class Response {

    public static <T> BaseResponse success() {
        return success(null, SUCCESS.getMessage());
    }

    public static <T> BaseResponse success(T data) {
        return success(data, SUCCESS.getMessage());
    }

    /**
     * 返回成功结果
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse success(T data, String message) {
        return new CommonResponse<T>(SUCCESS.getCode(), message, data);
    }

    public static ErrorResponse error(String message) {
        return new ErrorResponse(RESOURCE_NOT_FOUND.getCode(), message);
    }

    public static ErrorResponse error() {
        return new ErrorResponse(RESOURCE_NOT_FOUND.getCode(), RESOURCE_NOT_FOUND.getMessage());
    }
}
