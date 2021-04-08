package com.example.algo.sort;

/**
 * 快速排序
 */
public class Test {

    public void driver(ICar car){
        car.run();
    }

    public interface IDriver{

    }

    public interface ICar{
        void run();
    }

    class BMW implements ICar{
        @Override
        public void run() {
            System.out.println("BMW run.");
        }
    }

    class Benz implements ICar{
        @Override
        public void run() {
            System.out.println("Benz run.");
        }
    }

}
