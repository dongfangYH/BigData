package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuanhang.liu@tcl.com
 * @description BaseException
 * @date 2020-06-04 14:52
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private IResponseEnum responseEnum;
    private Object[] args;
    private String message;

    public BaseException(Throwable cause, IResponseEnum responseEnum, Object[] args, String message) {
        super(message, cause);
        this.responseEnum = responseEnum;
        this.args = args;
        this.message = message;
    }
}
