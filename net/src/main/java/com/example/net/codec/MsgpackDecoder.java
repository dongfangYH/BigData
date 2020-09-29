package com.example.net.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-28 16:54
 **/
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        final byte[] array;
        final int len = byteBuf.readableBytes();
        array = new byte[len];
        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, len);
        MessagePack messagePack = new MessagePack();
        list.add(messagePack.read(array));
    }
}
