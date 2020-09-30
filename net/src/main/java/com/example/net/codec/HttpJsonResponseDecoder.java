package com.example.net.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-30 10:07
 **/
public class HttpJsonResponseDecoder extends AbstractHttpJsonDecoder<FullHttpResponse> {

    public HttpJsonResponseDecoder(Class<?> clazz) {
        super(clazz);
    }

    public HttpJsonResponseDecoder(Class<? extends FullHttpResponse> inboundMessageType, Class<?> clazz) {
        super(inboundMessageType, clazz);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out) throws Exception {
        HttpJsonResponse response = new HttpJsonResponse(msg, decode0(ctx, msg.content()));
        out.add(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
