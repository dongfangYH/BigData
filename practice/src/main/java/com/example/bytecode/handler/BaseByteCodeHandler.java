package com.example.bytecode.handler;

import com.example.bytecode.type.ClassFile;

import java.nio.ByteBuffer;

public interface BaseByteCodeHandler {

    /**
     * 排序
     * @return
     */
    int order();

    /**
     * 解析
     * @param codeBuf
     * @param classFile
     * @throws Exception
     */
    void read(ByteBuffer codeBuf, ClassFile classFile) throws Exception;
}
