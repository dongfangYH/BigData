package com.example.net;

import com.example.net.codec.HttpJsonRequestDecoder;
import com.example.net.codec.HttpJsonResponseEncoder;
import com.example.net.codec.JsonReq;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-30 10:15
 **/
public class HttpJsonServer {

    public static void main(String[] args) throws Exception{
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(4);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.ERROR))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 解码http请求
                            ch.pipeline().addLast("httpDecoder", new HttpRequestDecoder());
                            ch.pipeline().addLast("httpAggregator", new HttpObjectAggregator(65536));
                            // 解码http + json请求
                            ch.pipeline().addLast("httpJsonRequestDecoder", new HttpJsonRequestDecoder(JsonReq.class));
                            //编码http response 应答
                            ch.pipeline().addLast("httpResponseEncoder", new HttpResponseEncoder());
                            // 编码http json response 应答
                            ch.pipeline().addLast("httpJsonResponseEncoder", new HttpJsonResponseEncoder());
                            ch.pipeline().addLast("httpServerHandler", new HttpServerHandler());
                        }
                    });
            ChannelFuture channelFuture = b.bind(8001).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
