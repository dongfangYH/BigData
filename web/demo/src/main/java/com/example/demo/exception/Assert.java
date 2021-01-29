package com.example.demo.exception;


/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-04 14:49
 **/
public interface Assert {

    /**
     * 创建异常
     *
     * @param args
     * @return
     */
    BaseException newException(Object... args);

    BaseException newException(String message, Object... args);

    /**
     * 创建异常
     *
     * @param t
     * @param args
     * @return
     */
    BaseException newException(Throwable t, Object... args);

}
