package com.example.net.codec;

import com.example.net.Message;
import com.example.net.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Converter;

import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-28 16:54
 **/
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final MessagePack messagePack = new MessagePack();

    private final Class type;

    {
        messagePack.register(Message.class);
        messagePack.register(Response.class);
    }

    public MsgpackDecoder(Class type) {
        this.type = type;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        final byte[] array;
        final int len = byteBuf.readableBytes();
        array = new byte[len];
        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, len);

        Value value = messagePack.read(array);
        list.add(new Converter(value).read(type));
    }
}
