package com.example.thread;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-16 14:55
 **/
public class CPTest {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition notFull = lock.newCondition();
    private static final Condition notEmpty = lock.newCondition();

    private static final LinkedList<String> queue = new LinkedList<>();
    private static volatile int currentSize = 0;
    private static final int maxSize = 20;

    public static void main(String[] args){

        for (int i = 1; i <= 2; i++){
            Thread t = new Thread(new ProducerTask(), "producer-" + i);
            t.start();
        }

        for (int i = 1; i <= 2; i++){
            Thread t = new Thread(new ConsumerTask(), "consumer-" + i);
            t.start();
        }

    }


    private static final class ProducerTask implements Runnable{

        @Override
        public void run() {
            while (true){
                try{
                    Thread.sleep(3);
                    lock.lock();
                    if (currentSize < maxSize){
                        queue.addLast(UUID.randomUUID().toString());
                        System.out.println(Thread.currentThread().getName() + " add 1 element to queue, now queue size is : "+ ++currentSize);
                        notEmpty.signalAll();
                    }else {
                        while (currentSize == maxSize){
                            notFull.await();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

            }
        }
    }

    private static final class ConsumerTask implements Runnable{

        @Override
        public void run() {
            while (true){
                try{
                    Thread.sleep(2);
                    lock.lock();
                    if (currentSize > 0){
                        String element = queue.removeFirst();
                        System.out.println(Thread.currentThread().getName() + " consume : " + element);
                        --currentSize;
                        notFull.signalAll();
                    }else {
                        while (currentSize == 0){
                            notEmpty.await();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

            }
        }
    }
}
