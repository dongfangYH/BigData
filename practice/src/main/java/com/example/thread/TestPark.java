package com.example.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-16 10:25
 **/
public class TestPark {

    public void parkSelf(){
        System.out.println(Thread.currentThread().getName() + " start to park..");
        LockSupport.park(this);
        System.out.println(Thread.currentThread().getName() + " end park..");
    }

    public static void main(String[] args) throws Exception{
        TestPark testPark = new TestPark();
        testPark.parkSelf();
    }
}
