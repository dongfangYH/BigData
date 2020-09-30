package com.example.net;

import com.example.net.codec.HttpJsonRequest;
import com.example.net.codec.HttpJsonResponse;
import com.example.net.codec.JsonReq;
import com.example.net.codec.JsonResp;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.nio.charset.Charset;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-30 10:22
 **/
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpJsonRequest> {

    private int counter;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpJsonRequest msg) throws Exception {
        HttpRequest request = msg.getRequest();
        JsonReq body = (JsonReq) msg.getBody();

        System.out.println("server receive : " + body.toString());

        JsonResp resp = new JsonResp();
        resp.setType(0);
        resp.setContent("server resp " + counter++ + ";");
        ChannelFuture future = ctx.writeAndFlush(new HttpJsonResponse(null, resp));
        if (!HttpUtil.isKeepAlive(request)){
            future.addListener(future1 -> ctx.close());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()){
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, Unpooled.copiedBuffer("error: " + status.reasonPhrase() + "\r\n", Charset.forName("UTF-8")));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
