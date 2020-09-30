package com.example.net;

import com.example.net.codec.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;


/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-30 10:37
 **/
public class HttpJsonClient {

    public static void main(String[] args) throws Exception{
        EventLoopGroup worker = new NioEventLoopGroup(1);
        try{
            Bootstrap b = new Bootstrap();
            b.group(worker)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 解码http 请求
                            ch.pipeline().addLast("httpDecoder", new HttpResponseDecoder());
                            ch.pipeline().addLast("httpAggregator", new HttpObjectAggregator(65536));
                            // 解码 http + json response 响应
                            ch.pipeline().addLast("httpJsonResponseDecoder", new HttpJsonResponseDecoder(JsonResp.class));
                            ch.pipeline().addLast("httpRequestEncoder", new HttpRequestEncoder());
                            ch.pipeline().addLast("httpJsonRequestEncoder", new HttpJsonRequestEncoder());
                            ch.pipeline().addLast("httpClientHandler", new HttpClientHandler());
                        }
                    });

            ChannelFuture future = b.connect("127.0.0.1", 8001).sync();
            future.channel().closeFuture().sync();
        }finally {
            worker.shutdownGracefully();
        }
    }
}
