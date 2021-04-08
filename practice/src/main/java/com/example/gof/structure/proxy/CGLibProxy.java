package com.example.gof.structure.proxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class CGLibProxy {
    public static void main(String[] args){
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, System.getProperty("user.dir") + "/designPattern/target");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceImpl.class);
        enhancer.setCallback(new UserServiceSayHiMethodInterceptor());
        UserService userService = (UserService) enhancer.create();
        userService.sayHi();
    }
}
