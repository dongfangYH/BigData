package com.example.net.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-07 16:10
 **/
public class ChannelReadHandler implements Runnable{

    private SelectionKey key;

    public ChannelReadHandler(SelectionKey key) {
        this.key = key;
    }

    @Override
    public void run() {
        SocketChannel client = (SocketChannel) key.attachment();
        try{
            ByteBuffer reqBf = ByteBuffer.allocate(1024);
            while (client.isOpen() && client.read(reqBf) != -1){
                // 表明读到数据
                if (reqBf.position() > 0) break;
            }
            if (reqBf.position() == 0) return;

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
