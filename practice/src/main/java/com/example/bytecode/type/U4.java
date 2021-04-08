package com.example.bytecode.type;

public class U4 extends U{

    public U4(byte b1, byte b2, byte b3, byte b4) {
        setValue(new byte[]{b1, b2, b3, b4});
    }

    public Integer toInt(){
        int a = (getValue()[0] & 0xff) << 24;
        a |= (getValue()[1] & 0xff) << 16;
        a |= (getValue()[2] & 0xff) << 8;
        return a | (getValue()[3] & 0xff);
    }

}
