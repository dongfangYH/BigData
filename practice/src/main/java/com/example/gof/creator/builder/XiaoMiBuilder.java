package com.example.gof.creator.builder;

public class XiaoMiBuilder extends PhoneBuilder<XiaoMi>{
    @Override
    XiaoMi build() {
        try {
            if (getaClass() != null)
                return getaClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}