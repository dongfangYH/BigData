package com.example.cp;


public class Consumer implements Runnable{

    public Consumer() {
    }

    @Override
    public void run() {

        while (true){
            try{
                Client.lock.lock();
                if (Client.bonusPackage.size() == 0){
                    Client.notEmpty.await();
                }else {
                    String bonus = Client.bonusPackage.poll();
                    System.out.println(Thread.currentThread().getName() + " consume " + bonus);
                    Client.notFull.signal();
                }
            }catch (Exception e){

            }finally {
                Client.lock.unlock();
            }
        }

    }
}
