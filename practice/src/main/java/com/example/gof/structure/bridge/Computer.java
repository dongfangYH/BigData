package com.example.gof.structure.bridge;

public abstract class Computer extends Product{
    private String type;

    public Computer(double price, String name, String type) {
        super(price, name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public double getTotalPrice() {
        return "laptop".equals(type) ? 1.2d * getPrice() : getPrice();
    }
}
