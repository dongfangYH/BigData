package com.example;

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
 * @date 2020-08-30 15:41
 **/
public class Nio {

    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));

        Selector selector = Selector.open();

        SelectionKey selectionKey = serverSocketChannel.register(selector, 0, serverSocketChannel);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);// serverSocket只支持accept

        while (true){
            //阻塞，直到有通知才返回
            selector.select();

            Set<SelectionKey> keys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = keys.iterator();

            while (keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();

                keyIterator.remove();

                if (key.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) key.attachment();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, socketChannel);
                }

                if (key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) key.attachment();
                    ByteBuffer bf = ByteBuffer.allocate(1024);

                    try{
                        while (socketChannel.isOpen() && socketChannel.read(bf) != -1) {
                            if (bf.position() > 0) break;
                        }

                        if (bf.position() == 0) continue;
                        bf.flip();
                        byte[] content = new byte[bf.limit()];
                        bf.get(content);
                        String command = new String(content);
                        if ("exit".equalsIgnoreCase(command)){
                            System.exit(0);
                        }
                        System.out.println(command);

                        ByteBuffer respBf = ByteBuffer.wrap("ok\r\n".getBytes());
                        while (respBf.hasRemaining()){
                            socketChannel.write(respBf);
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
