package com.example.test;

public class Main {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
           while (true){
               try {
                   Thread.sleep(1000l);
                   System.out.println("Thread running...");
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        });
        t.setDaemon(true);
        t.start();
    }
}
