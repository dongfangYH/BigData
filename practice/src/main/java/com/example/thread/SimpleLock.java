package com.example.thread;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author yuanhang.liu@tcl.com
 * @description unfair lock
 * @date 2020-11-17 10:54
 **/
public class SimpleLock implements ILock{

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    private static final class Sync extends AbstractQueuedSynchronizer{
        public Sync() {
            setState(1);
        }

        @Override
        protected boolean tryAcquire(int arg) {
             if (compareAndSetState(1, 0)){
                 setExclusiveOwnerThread(Thread.currentThread());
                 return true;
             }
             return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getExclusiveOwnerThread() == Thread.currentThread()){
                setState(1);
                setExclusiveOwnerThread(null);
            }
            throw new IllegalMonitorStateException();
        }
    }
}
