package com.example.gof.structure.bridge;

public abstract class Fruit extends Product{
    private double quality;

    public double getQuality() {
        return quality;
    }

    public Fruit(double price, String name, double quality) {
        super(price, name);
        this.quality = quality;
    }

    @Override
    public double getTotalPrice() {
        return getQuality() * getPrice();
    }
}
