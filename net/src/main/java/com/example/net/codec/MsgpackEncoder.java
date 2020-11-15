package com.example.net.codec;

import com.example.net.Message;
import com.example.net.ReqOuterClass;
import com.example.net.RespOuterClass;
import com.example.net.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-28 16:52
 **/
public class MsgpackEncoder extends MessageToByteEncoder<Object> {
    private final MessagePack messagePack = new MessagePack();

    {
        messagePack.register(Message.class);
        messagePack.register(Response.class);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byte[] raw = messagePack.write(o);
        byteBuf.writeBytes(raw);
    }
}
