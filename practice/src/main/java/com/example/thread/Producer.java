package com.example.thread;

public class Producer implements Runnable{

    public Producer() {
    }

    @Override
    public void run() {
        int bonus = 1;
        while (true){
            try {
                Client.lock.lock();
                if (Client.bonusPackage.size() >= 10){
                    Client.notFull.await();
                }else {
                    String bonusStr = "Bonus-"+ (bonus++);
                    Client.bonusPackage.add(bonusStr);
                    System.out.println(Thread.currentThread().getName() + " produce " + bonusStr);
                    Client.notEmpty.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                Client.lock.unlock();
            }
        }
    }
}
