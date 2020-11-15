package com.example;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.UUID;

public class TestUnSafe {
    private static final Unsafe THE_UNSAFE;
    static{
        try {
            final PrivilegedExceptionAction<Unsafe> action = new PrivilegedExceptionAction<Unsafe>() {
                public Unsafe run() throws Exception {
                    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafe.setAccessible(true);
                    return (Unsafe) theUnsafe.get(null);
                }
            };
            THE_UNSAFE = AccessController.doPrivileged(action);
        }
        catch (Exception e){
            throw new RuntimeException("Unable to load unsafe", e);
        }
    }

    public static void main(String[] args) throws Exception{

        MyObject myObject = new MyObject();
        myObject.setId(UUID.randomUUID().toString());
        myObject.setName("myObject");
        myObject.setTime(System.currentTimeMillis());

        Field field = MyObject.class.getDeclaredField("time");
        field.setAccessible(true);

        Unsafe unsafe = THE_UNSAFE;
        long fieldOffset = unsafe.objectFieldOffset(field);
        System.out.println("swap before : " + myObject.toString());
        boolean result = unsafe.compareAndSwapLong(myObject, fieldOffset, myObject.getTime(), 1111111l);
        System.out.println("swap -> " + (result ? "success!" : "fail!"));
        System.out.println("swap after : " + myObject.toString());
    }
}
