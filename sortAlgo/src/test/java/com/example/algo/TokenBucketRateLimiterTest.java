package com.example.algo;

import junit.framework.TestCase;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-13 11:12
 **/
public class TokenBucketRateLimiterTest extends TestCase {

    private static final RateLimiter r = new TokenBucketRateLimiter(10);

    public void testTryAcquire(){
        for (int i = 0; i < 11; i++){
            System.out.println(r.tryAcquire());
        }
    }
}
