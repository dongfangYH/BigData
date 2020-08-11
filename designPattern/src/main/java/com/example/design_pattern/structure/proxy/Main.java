package com.example.design_pattern.structure.proxy;

import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args){
        House house = new House(1L, "HANGZHOU", 121d, 9000d);
        Landlord landlord = new Landlord();
        HouseSellerProxy handler = new HouseSellerProxy(landlord);
        ClassLoader loader = landlord.getClass().getClassLoader();
        Class[] interfaces = landlord.getClass().getInterfaces();

        HouseKeeper proxy = (HouseKeeper) Proxy.newProxyInstance(loader, interfaces, handler);
        proxy.sellHouse(house);
    }
}
