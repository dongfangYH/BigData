package com.example.algo;

/**
 * @author yuanhang.liu@tcl.com
 * @description Rate Limiter interface
 * @date 2020-11-13 09:32
 **/
public interface RateLimiter {

    void acquire() throws InterruptedException;

    boolean tryAcquire();
}
