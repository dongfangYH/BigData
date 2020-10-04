package com.example.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-10-04 17:39
 **/
public class ByteBufTest {

    public static void main(String[] args){
        ByteBuf buf = Unpooled.buffer(10, 100);
        int writeIdx = buf.writerIndex();
        // 4位占位
        buf.writeBytes(new byte[]{0, 0, 0, 0});

    }
}
