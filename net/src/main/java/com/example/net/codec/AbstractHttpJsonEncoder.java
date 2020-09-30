package com.example.net.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.Charset;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-30 09:22
 **/
public abstract class AbstractHttpJsonEncoder<T> extends MessageToMessageEncoder<T> {

    final static Charset UTF8 = Charset.forName("UTF-8");

    protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) throws Exception{
        String jsonStr = GsonUtils.toJson(body);
        ByteBuf buf = Unpooled.copiedBuffer(jsonStr.getBytes(UTF8));
        return buf;
    }
}
