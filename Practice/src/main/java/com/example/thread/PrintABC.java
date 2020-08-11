package com.example.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC {

    private volatile static int state = 0;
    private static Lock lock = new ReentrantLock();
    private static Condition sub0 = lock.newCondition();
    private static Condition sub1 = lock.newCondition();
    private static Condition sub2 = lock.newCondition();


    public static void main(String[] args) throws Exception{

        Runnable a = () -> {
            while (true){
                try{
                    lock.lock();
                    if (state % 3 != 0){
                        sub0.await();
                    }
                    System.out.println("A");
                    state++;
                    if (state % 3 == 1){
                        sub1.signal();
                    }
                    if (state % 3 == 2){
                        sub2.signal();
                    }
                }catch (Exception e){
                }finally {
                    lock.unlock();
                }
            }
        };

        Runnable b = () -> {
            while (true){
                try{
                    lock.lock();
                    if (state % 3 != 1){
                        sub1.await();
                    }
                    System.out.println("B");
                    state++;
                    if (state % 3 == 0){
                        sub0.signal();
                    }
                    if (state % 3 == 2){
                        sub2.signal();
                    }
                }catch (Exception e){
                }finally {
                    lock.unlock();
                }
            }
        };

        Runnable c = () -> {
            while (true){
                try{
                    lock.lock();
                    if (state % 3 != 2){
                        sub2.await();
                    }
                    System.out.println("C");
                    state++;
                    if (state % 3 == 0){
                        sub0.signal();
                    }
                    if (state % 3 == 1){
                        sub1.signal();
                    }
                }catch (Exception e){
                }finally {
                    lock.unlock();
                }
            }
        };

        Thread ta = new Thread(a, "Thread-A");
        Thread tb = new Thread(b, "Thread-B");
        Thread tc = new Thread(c, "Thread-C");

        ta.start();tb.start();tc.start();

        ta.join();

    }

}
