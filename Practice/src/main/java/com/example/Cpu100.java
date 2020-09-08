package com.example;

import java.util.Random;

public class Cpu100 {

    public static void main(String[] args) throws Exception{
        Thread t = new Thread(() -> {
            Random r = new Random(100);
            for (;;){
                int i = 100 % (r.nextInt() + 1);
                try {
                    System.out.println(i);
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        t.join();

    }
}
