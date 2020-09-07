package com.example.net.nio;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
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

        Dispatcher dispatcher = new Dispatcher(selector);

        while (true){
            // 事件发生时会通知，没有事件则阻塞
            selector.select();
            // 获取要一批事件
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()){
                SelectionKey key = it.next();
                it.remove();
                dispatcher.dispatch(key);
            }
            selector.selectNow();
        }

    }


}
