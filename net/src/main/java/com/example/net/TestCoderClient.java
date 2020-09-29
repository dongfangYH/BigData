package com.example.net;

import com.example.net.codec.MsgpackDecoder;
import com.example.net.codec.MsgpackEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import org.msgpack.MessagePack;

import java.util.Random;
import java.util.UUID;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-28 17:18
 **/
public class TestCoderClient {

    public static void main(String[] args) throws Exception{
        EventLoopGroup worker = new NioEventLoopGroup(1);
        try{
            Bootstrap client = new Bootstrap();
            client.group(worker)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            channel.pipeline().addLast(new ProtobufDecoder(RespOuterClass.Resp.getDefaultInstance()));
                            channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            channel.pipeline().addLast(new ProtobufEncoder());
                            //p.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0,2));
                            //p.addLast("msgPackDecoder", new MsgpackDecoder());
                            //p.addLast("frameEncoder", new LengthFieldPrepender(2));
                            //p.addLast("msgPackEncoder", new MsgpackEncoder());
                            channel.pipeline().addLast(new TestCoderClientHandler());
                        }
                    });
            ChannelFuture future = client.connect("127.0.0.1", 10000).sync();
            future.channel().closeFuture().sync();
        }finally {
            worker.shutdownGracefully();
        }
    }

    private static class TestCoderClientHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Random random = new Random();
            for (int i = 0; i < 100; i++){
                ReqOuterClass.Req req = ReqOuterClass.Req.newBuilder().setId(UUID.randomUUID().toString())
                        .setContent("message: " + i)
                        .setType(random.nextInt(6))
                .build();
                ctx.write(req);
            }

            ctx.flush();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            RespOuterClass.Resp resp = (RespOuterClass.Resp) msg;
            System.out.println("receive from server : " + resp.toString());
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
