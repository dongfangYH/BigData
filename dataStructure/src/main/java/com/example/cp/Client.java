package com.example.cp;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client {

    static final LinkedList<String> bonusPackage = new LinkedList<>();
    static final Lock lock = new ReentrantLock();
    static final Condition notEmpty = lock.newCondition();
    static final Condition notFull = lock.newCondition();

    public static void main(String[] args) throws Exception{

        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        Thread cThread = new Thread(consumer, "consumer-Thread");
        Thread pThread = new Thread(producer, "producer-Thread");

        pThread.start();
        cThread.start();

        pThread.join();

    }

}
