package com.example.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-05 17:00
 **/
public class NioServer {

    public static void main(String[] args) throws Exception{
        ServerSocketChannel sc = ServerSocketChannel.open();
        sc.socket().bind(new InetSocketAddress(7777));
        sc.configureBlocking(false);//非阻塞io

        Selector selector = Selector.open();

        SelectionKey selectionKey = sc.register(selector, 0, sc);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        System.out.println("Nio服务启动成功");
        while (true){
            // 事件发生时会通知，没有事件则阻塞
            selector.select();

            // 获取要一批事件
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()){
                SelectionKey key = it.next();
                it.remove();

                // accept的key
                if (key.isAcceptable()){
                    // 接收新的客户端连接
                    ServerSocketChannel server  = (ServerSocketChannel) key.attachment();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ, client);
                }

                if (key.isReadable()){
                    SocketChannel client = (SocketChannel) key.attachment();
                    try {
                        ByteBuffer reqBf = ByteBuffer.allocate(1024);
                        while (client.isOpen() && client.read(reqBf) != -1){
                            // 表明读到数据
                            if (reqBf.position() > 0) break;
                        }
                        if (reqBf.position() == 0) continue;

                        //转换为读
                        reqBf.flip();
                        byte[] content = new byte[reqBf.limit()];
                        reqBf.get(content);
                        //客户端发来的数据
                        System.out.println(new String(content));

                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Length: 11\r\n\r\n" +
                                "Hello World";

                        ByteBuffer respBf = ByteBuffer.wrap(response.getBytes());
                        // 往客户端写数据，由于不一定一次写完，所以循环写
                        while (respBf.hasRemaining()){
                            client.write(respBf);
                        }
                    }catch (IOException e){
                        key.cancel();
                    }
                }
            }
            selector.selectNow();
        }

    }
}
