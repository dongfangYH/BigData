package com.example.net.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.Charset;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-30 09:32
 **/
public abstract class AbstractHttpJsonDecoder<T> extends MessageToMessageDecoder<T> {

    private Class<?> clazz;
    static final Charset UTF8 = Charset.forName("UTF-8");

    public AbstractHttpJsonDecoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    public AbstractHttpJsonDecoder(Class<? extends T> inboundMessageType, Class<?> clazz) {
        super(inboundMessageType);
        this.clazz = clazz;
    }

    protected Object decode0(ChannelHandlerContext ctx, ByteBuf body){
        String jsonStr = body.toString(UTF8);
        Object result = GsonUtils.fromJson(jsonStr, clazz);
        return result;
    }
}
