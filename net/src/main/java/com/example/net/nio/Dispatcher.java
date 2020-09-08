package com.example.net.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-07 16:14
 **/
public class Dispatcher {

    private Selector selector;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() << 2);

    public Dispatcher(Selector selector) {
        this.selector = selector;
    }

    public void dispatch(SelectionKey key) throws IOException {
        // accept的key
        if (key.isAcceptable()){
            // 接收新的客户端连接
            ServerSocketChannel server  = (ServerSocketChannel) key.attachment();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ, client);
        }

        if (key.isReadable()){
            executorService.execute(new ChannelReadHandler(key));
        }
    }
}
