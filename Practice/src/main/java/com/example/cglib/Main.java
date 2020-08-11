package com.example.cglib;

import net.sf.cglib.beans.*;
import net.sf.cglib.core.Converter;
import net.sf.cglib.core.KeyFactory;
import net.sf.cglib.proxy.*;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.Method;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-03 17:18
 **/
public class Main {

    public static final void intercept(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Sample.class);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("===pre===");
            Object result = methodProxy.invokeSuper(o, objects);
            System.out.println("===aft===");
            return result;
        });
        Sample sample = (Sample) enhancer.create();
        sample.func();
    }

    public static final void invocation(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Sample.class);
        enhancer.setCallback((InvocationHandler) (o, method, objects) -> {
            System.out.println("===pre===");
            Object result = method.invoke(o, objects);
            System.out.println("===aft===");
            return result;
        });
        Sample sample = (Sample) enhancer.create();
        sample.func();
    }

    public static final void callBackFilter(){
        Enhancer enhancer = new Enhancer();
        CallbackHelper callbackHelper = new CallbackHelper(Sample.class, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                if(method.getDeclaringClass() != Object.class && method.getReturnType() == String.class){
                    return new FixedValue() {
                        @Override
                        public Object loadObject() throws Exception {
                            return "Hello cglib";
                        }
                    };
                }else{
                    return NoOp.INSTANCE;
                }
            }
        };
        enhancer.setSuperclass(Sample.class);
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        Sample proxy = (Sample) enhancer.create();
        proxy.func();
    }

    public static final void immutableBean(){
        Sample bean = new Sample();
        bean.setValue("Hello world");
        Sample immutableBean = (Sample) ImmutableBean.create(bean); //创建不可变类
        System.out.println(immutableBean.getValue());
        bean.setValue("Hello world, again"); //可以通过底层对象来进行修改
        System.out.println(immutableBean.getValue());
        immutableBean.setValue("Hello cglib"); //直接修改将throw exception
        System.out.println(immutableBean.getValue());
    }

    public static final void generateBean() throws Exception{
        BeanGenerator beanGenerator = new BeanGenerator();
        beanGenerator.addProperty("value",String.class);
        Object myBean = beanGenerator.create();
        Method setter = myBean.getClass().getMethod("setValue",String.class);
        setter.invoke(myBean,"Hello cglib");

        Method getter = myBean.getClass().getMethod("getValue");
        System.out.println(getter.invoke(myBean));
    }

    public static final void copyBean(){
        BeanCopier copier = BeanCopier.create(Sample.class, Sample2.class, true);//设置为true，则使用converter
        Sample myBean = new Sample();
        myBean.setValue("Hello cglib");
        Sample2 otherBean = new Sample2();
        copier.copy(myBean, otherBean, (o, aClass, o1) -> "null"); //设置为true，则传入converter指明怎么进行转换
        System.out.println(otherBean.getValue());
    }

    //避免每次进行BulkBean.create创建对象，一般将其声明为static的
    public static final void bulkBean(){
        BulkBean bulkBean = BulkBean.create(Sample.class,
                new String[]{"getValue"},
                new String[]{"setValue"},
                new Class[]{String.class});
        Sample bean = new Sample();
        bean.setValue("Hello world");
        Object[] propertyValues = bulkBean.getPropertyValues(bean);
        System.out.println(bulkBean.getPropertyValues(bean).length);
        System.out.println(bulkBean.getPropertyValues(bean)[0]);
        bulkBean.setPropertyValues(bean,new Object[]{"Hello cglib"});
        System.out.println(bean.getValue());
    }

    public static final void beanMap() throws Exception{
        BeanGenerator generator = new BeanGenerator();
        generator.addProperty("username",String.class);
        generator.addProperty("password",String.class);
        Object bean = generator.create();
        Method setUserName = bean.getClass().getMethod("setUsername", String.class);
        Method setPassword = bean.getClass().getMethod("setPassword", String.class);
        setUserName.invoke(bean, "admin");
        setPassword.invoke(bean,"password");
        BeanMap map = BeanMap.create(bean);
        System.out.println(map.toString());
    }

    /**
     * keyFactory类用来动态生成接口的实例，接口需要只包含一个newInstance方法，
     * 返回一个Object。keyFactory为构造出来的实例动态生成了Object.equals和Object.hashCode方法，
     * 能够确保相同的参数构造出的实例为单例的。
     */
    public static final void keyFactory(){
        SampleKeyFactory factory = (SampleKeyFactory) KeyFactory.create(SampleKeyFactory.class);
        Object object = factory.newInstance("1");
    }

    public static final void fastClass() throws Exception{
        FastClass fastClass = FastClass.create(Sample.class);
        FastMethod fastMethod = fastClass.getMethod("getValue",new Class[0]);
        Sample bean = new Sample();
        bean.setValue("Hello world");
        System.out.println(fastMethod.invoke(bean, new Object[0]));
    }

    public static void main(String[] args) throws Exception{
        fastClass();
    }
}
