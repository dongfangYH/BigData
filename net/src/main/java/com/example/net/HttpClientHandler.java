package com.example.net;

import com.example.net.codec.HttpJsonRequest;
import com.example.net.codec.HttpJsonResponse;
import com.example.net.codec.JsonReq;
import com.example.net.codec.JsonResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.UUID;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-30 10:51
 **/
public class HttpClientHandler extends SimpleChannelInboundHandler<HttpJsonResponse> {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpJsonResponse msg) throws Exception {
        JsonResp resp = (JsonResp) msg.getBody();
        System.out.println("client receive : " + resp.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        JsonReq req = new JsonReq();
        req.setId(UUID.randomUUID().toString());
        req.setContent("content from client");
        req.setSignature(InetAddress.getLocalHost().getHostName());
        HttpJsonRequest request = new HttpJsonRequest(null, req);
        ctx.writeAndFlush(request);
    }
}
