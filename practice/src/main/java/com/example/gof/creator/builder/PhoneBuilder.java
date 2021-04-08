package com.example.gof.creator.builder;

public abstract class PhoneBuilder<T extends Phone> {

    /**
     * 生产手机
     */
    abstract T build();

    Class<? extends T> aClass;

    public Class<? extends T> getaClass() {
        return aClass;
    }

    public void setaClass(Class<? extends T> aClass) {
        this.aClass = aClass;
    }
}
