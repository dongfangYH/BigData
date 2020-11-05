package com.example;

import java.lang.reflect.Proxy;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-02 09:42
 **/
public class JdkProxy {
    public static void main(String[] args){
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        ClassLoader classLoader = JdkProxy.class.getClassLoader();
        IFunc func = (IFunc) Proxy.newProxyInstance(classLoader,
                       new Class[]{IFunc.class},
                       new ProxyFunc(new DefaultFunc()));
        func.doAction();
    }
}
