package com.example.design_pattern.structure.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HouseSellerProxy implements InvocationHandler {

    private HouseKeeper houseKeeper;

    public HouseSellerProxy(HouseKeeper houseKeeper) {
        this.houseKeeper = houseKeeper;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("do before invoke...");
        Object result = method.invoke(houseKeeper, args);
        System.out.println("do after invoke...");
        return result;
    }
}
