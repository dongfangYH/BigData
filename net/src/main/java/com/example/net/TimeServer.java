package com.example.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-28 15:07
 **/
public class TimeServer {

    public static void main(String[] args) throws Exception{
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(2);

        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss, worker)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            channel.pipeline().addLast(new StringDecoder());
                            channel.pipeline().addLast(new TimeServerHandler());
                        }
                    });

            ChannelFuture future = server.bind(9999).sync();
            future.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    private static class TimeServerHandler extends ChannelInboundHandlerAdapter{

        private long counter;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            String read = (String) msg;

            String resp = "";
            if ("$QUERY TIME".equals(read)){
                resp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            }else {
                resp = "UNKNOWN ORDER";
            }
            resp += System.getProperty("line.separator");
            ByteBuf respBuf = Unpooled.wrappedBuffer(resp.getBytes());
            ctx.write(respBuf);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
            System.out.println("read client msg complete : " + ++counter);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }
}
