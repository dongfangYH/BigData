package com.example.net;

import com.example.net.codec.MsgpackDecoder;
import com.example.net.codec.MsgpackEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-28 17:02
 **/
public class TestCoderServer {

    public static void main(String[] args) throws Exception{
        EventLoopGroup accepterGroup = new NioEventLoopGroup(1);
        EventLoopGroup ioGroup = new NioEventLoopGroup(2);

        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(accepterGroup, ioGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            //channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            //channel.pipeline().addLast(new ProtobufDecoder(ReqOuterClass.Req.getDefaultInstance()));
                            //channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            //channel.pipeline().addLast(new ProtobufEncoder());
                            channel.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                            channel.pipeline().addLast("msgPackDecoder", new MsgpackDecoder(Message.class));
                            channel.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                            channel.pipeline().addLast("msgPackEncoder", new MsgpackEncoder());
                            channel.pipeline().addLast("serverHandler", new TestCoderServerHandler());
                        }
                    });
            // 通过bind启动服务
            ChannelFuture f = b.bind(10000).sync();
            //阻塞主线程，直到网络服务关闭
            f.channel().closeFuture().sync();
        }finally {
            accepterGroup.shutdownGracefully();
            ioGroup.shutdownGracefully();
        }
    }

    private static class TestCoderServerHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

           // ReqOuterClass.Req req = (ReqOuterClass.Req) msg;
            Message message = (Message) msg;
            System.out.println("receive client request, " + message.toString());

            /*RespOuterClass.Resp resp = RespOuterClass.Resp.newBuilder()
                    .setType(1)
                    .setContent("server response.")
                    .build();*/
            Response resp = new Response();
            resp.setType(1);
            resp.setContent("content from server.");

            ctx.write(resp);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
