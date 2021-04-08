package com.example.bytecode.handler;

import com.example.bytecode.type.ClassFile;

import java.nio.ByteBuffer;

public class VersionHandler implements BaseByteCodeHandler{
    @Override
    public int order() {
        return 1;
    }

    @Override
    public void read(ByteBuffer codeBuf, ClassFile classFile) throws Exception {

    }
}
