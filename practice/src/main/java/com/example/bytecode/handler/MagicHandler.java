package com.example.bytecode.handler;

import com.example.bytecode.type.ClassFile;

import java.nio.ByteBuffer;

public class MagicHandler implements BaseByteCodeHandler{

    @Override
    public int order() {
        return 0;
    }

    @Override
    public void read(ByteBuffer codeBuf, ClassFile classFile) throws Exception {

    }
}
