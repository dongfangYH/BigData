package com.example.gof.creator.builder;

public class RedMiBuilder extends PhoneBuilder<RedMi>{

    @Override
    RedMi build() {
        try {
            if (getaClass() != null)
                return getaClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
