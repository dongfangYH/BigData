package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadLocalTests {

    public static void main(String[] args) throws Exception{

        final ThreadLocal<Object> threadLocal1 = new ThreadLocal<>();
        final ThreadLocal<Object> threadLocal2 = new ThreadLocal<>();

        final ExecutorService threadPool = Executors.newFixedThreadPool(1000);

        Thread.sleep(10000L);
        for (int i = 0; i < 10000; i++){
            threadPool.submit(() -> {
                byte[] bytes = new byte[1024 * 1024];
                threadLocal1.set(bytes);

                threadLocal1.remove();
            });

            threadPool.submit(() -> {
                byte[] bytes = new byte[1024 * 1024];
                threadLocal2.set(bytes);

                threadLocal2.remove();
            });
        }

    }
}
