package com.example.cglib;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;

public class MetaSpaceOOM {

    public static void main(String[] args){
        ClassLoadingMXBean loadingBean = ManagementFactory.getClassLoadingMXBean();
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(MetaSpaceOOM.class);
            enhancer.setCallbackTypes(new Class[]{Dispatcher.class, MethodInterceptor.class});
            enhancer.setCallbackFilter(new CallbackFilter() {
                @Override
                public int accept(Method method) {
                    return 1;
                }

                @Override
                public boolean equals(Object obj) {
                    return super.equals(obj);
                }
            });

            Class clazz = enhancer.createClass();
            System.out.println(clazz.getName());
            //显示数量信息（共加载过的类型数目，当前还有效的类型数目，已经被卸载的类型数目）
            System.out.println("total: " + loadingBean.getTotalLoadedClassCount());
            System.out.println("active: " + loadingBean.getLoadedClassCount());
            System.out.println("unloaded: " + loadingBean.getUnloadedClassCount());
        }
    }
}
