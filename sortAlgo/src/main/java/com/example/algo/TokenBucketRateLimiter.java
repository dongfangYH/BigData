package com.example.algo;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-13 09:31
 **/
public class TokenBucketRateLimiter implements RateLimiter, AutoCloseable{

    private final int limit;
    private final Semaphore semaphore;
    private final Timer timer;
    private static final long SECOND = 1000l;

    public TokenBucketRateLimiter(int limit) {

        if (limit <= 0){
            throw new IllegalArgumentException("limit can not less than 1.");
        }

        this.limit = limit;
        this.semaphore = new Semaphore(limit);
        this.timer = new Timer();

        long delay = SECOND / limit;
        long period = SECOND / limit;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (semaphore.availablePermits() < limit){
                    semaphore.release();
                }
            }
        }, delay, period);
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public void acquire() throws InterruptedException {
        this.semaphore.acquire();
    }

    @Override
    public boolean tryAcquire() {
        return this.semaphore.tryAcquire();
    }

    @Override
    public void close() throws Exception {
        this.timer.cancel();
    }
}
