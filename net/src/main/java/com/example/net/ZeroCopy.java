package com.example.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-05 11:30
 **/
public class ZeroCopy {

    public static void main(String[] args){
        ByteBuf buffer1 = Unpooled.buffer(3);

        buffer1.writeBytes("666".getBytes());
        System.out.println(Arrays.toString(buffer1.array()));
        buffer1.writeBytes("jads".getBytes());
        System.out.println(Arrays.toString(buffer1.array()));
        buffer1.readBytes(5);
        System.out.println(buffer1);
        buffer1.readBytes(2);
        System.out.println(buffer1);
    }
}
