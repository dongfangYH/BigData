package com.example.bytecode.util;

import com.example.bytecode.handler.BaseByteCodeHandler;
import com.example.bytecode.handler.MagicHandler;
import com.example.bytecode.handler.VersionHandler;
import com.example.bytecode.type.ClassFile;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClassFileAnalyser {

    private final static List<BaseByteCodeHandler> handlers = new ArrayList<>();

    static {
        handlers.add(new MagicHandler());
        handlers.add(new VersionHandler());

        handlers.sort(Comparator.comparingInt(BaseByteCodeHandler::order));
    }

    public static ClassFile analysis(ByteBuffer codeBuf) throws Exception{
        codeBuf.position(0);
        ClassFile classFile = new ClassFile();
        for (BaseByteCodeHandler handler : handlers){
            handler.read(codeBuf, classFile);
        }
        return classFile;
    }
}
