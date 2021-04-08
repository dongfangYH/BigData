package com.example.gof.structure.proxy;

public class Landlord implements HouseKeeper{

    @Override
    public double sellHouse(House house) {
        System.out.println("sell house -> " + house.toString());
        return house.getTotalPrice();
    }
}
