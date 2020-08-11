package com.example.thread;

import java.util.concurrent.CyclicBarrier;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-04-16 09:44
 **/
public class TestCyclicBarrier {

    static final CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        public void run() {
            System.out.println("ding....");
        }
    });

    static void execute() throws Exception{
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("ping...");
                    cyclicBarrier.await();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("pong...");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }

    public static void main(String[] args) throws Exception {

        execute();
        Thread.sleep(1000L);
        execute();
        Thread.sleep(1000L);

    }
}
