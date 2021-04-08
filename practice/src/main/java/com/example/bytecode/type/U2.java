package com.example.bytecode.type;

public class U2 extends U{

    public U2(byte b1, byte b2) {
        setValue(new byte[]{b1, b2});
    }

    @Override
    public Integer toInt(){
        return (getValue()[0] & 0xff) << 8 | (getValue()[1] & 0xff);
    }

}
