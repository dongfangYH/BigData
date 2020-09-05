package com.example.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-05 14:44
 **/
public class EchoServer {
    public static void main(String[] args) throws Exception{
        EventLoopGroup accepterGroup = new NioEventLoopGroup(1);
        EventLoopGroup ioGroup = new NioEventLoopGroup(2);

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(accepterGroup, ioGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline p = sc.pipeline();
                            p.addLast(new EchoServerHandler());
                        }
                    });
            // 通过bind启动服务
            ChannelFuture f = b.bind(7077).sync();
            //阻塞主线程，直到网络服务关闭
            f.channel().closeFuture().sync();
        }finally {
            accepterGroup.shutdownGracefully();
            ioGroup.shutdownGracefully();
        }
    }
}
