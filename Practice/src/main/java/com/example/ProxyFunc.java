package com.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-02 09:47
 **/
public class ProxyFunc implements InvocationHandler {

    private IFunc func;

    public ProxyFunc(IFunc func) {
        this.func = func;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try{
            System.out.println("before invoke...");
            result = method.invoke(func, args);
            System.out.println("after invoke...");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
