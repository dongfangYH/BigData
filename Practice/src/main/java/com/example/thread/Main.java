package com.example.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception{
        DelayQueue<DelayObject> delayQueue = new DelayQueue();

        for (int i = 1; i <= 10; i++){
            DelayObject delayObject = new DelayObject(i * 1000);
            delayQueue.add(delayObject);
        }

        for (;;){
           DelayObject delayObject = delayQueue.poll();
           if (null == delayObject){
               System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
           }else {
               System.out.println(delayObject);
           }
           Thread.sleep(500);
        }
    }


    private static final class DelayObject implements Delayed {

        private final long time;

        private DelayObject(long time) {
            this.time = System.currentTimeMillis() + time;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return time - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            DelayObject dyo= (DelayObject) o;
            return (int)(time - dyo.time);
        }
    }

}
