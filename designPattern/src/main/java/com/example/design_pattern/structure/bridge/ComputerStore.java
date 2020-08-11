package com.example.design_pattern.structure.bridge;

public class ComputerStore extends Store<Computer>{

    public ComputerStore(Computer product) {
        super(product);
    }

    @Override
    public void sell() {
        Computer computer =  getProduct();
        System.out.println("sell computer : " + computer.getName() + " , " + computer.getTotalPrice());
    }
}
