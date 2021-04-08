package com.example.thread;

import junit.framework.TestCase;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-17 11:10
 **/
public class SimpleLockTest extends TestCase {


    public void testLockAndUnLock() throws Exception{
        final ILock lock = new SimpleLock();
        Thread t1 = new Thread(() -> {
            try{
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " get lock.");
                Thread.sleep(5000L);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName() + " unlock.");
                lock.unlock();
            }
        }, "thread1");


        Thread t2 = new Thread(() -> {
            try{
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " get lock.");
                Thread.sleep(4000L);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName() + " unlock.");
                lock.unlock();
            }
        }, "thread2");

        t1.start();
        t2.start();


        t1.join();
        t2.join();

    }
}
