package com.example.net.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyFixLengthDecoder extends ByteToMessageDecoder {

    private final int length;

    public MyFixLengthDecoder(int length) {
        this.length = length;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableLength = in.readableBytes();

        if (readableLength >= length){
            byte[] message = new byte[length];
            in.readBytes(message);
            out.add(new String(message, "UTF-8"));
        }
    }
}
