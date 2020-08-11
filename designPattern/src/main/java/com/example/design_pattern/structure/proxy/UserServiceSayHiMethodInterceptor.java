package com.example.design_pattern.structure.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UserServiceSayHiMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("enhance before.");
        Object object = methodProxy.invokeSuper(o, args);
        System.out.println("enhance after.");
        return object;
    }
}
