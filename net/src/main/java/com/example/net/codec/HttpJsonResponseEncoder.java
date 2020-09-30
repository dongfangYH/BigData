package com.example.net.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-30 10:03
 **/
public class HttpJsonResponseEncoder extends AbstractHttpJsonEncoder<HttpJsonResponse>{
    @Override
    protected void encode(ChannelHandlerContext ctx, HttpJsonResponse msg, List<Object> out) throws Exception {
        ByteBuf body = encode0(ctx, msg.getBody());
        FullHttpResponse response = msg.getResponse();
        if (response == null){
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
        }else {
            response = new DefaultFullHttpResponse(msg.getResponse().protocolVersion(),
                    msg.getResponse().status(), body);
        }
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        HttpUtil.setContentLength(response, body.readableBytes());
        out.add(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
