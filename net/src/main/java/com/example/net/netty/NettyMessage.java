package com.example.net.netty;

/**
 * @author yuanhang.liu@tcl.com
 * @description @Link https://github.com/wesker8080/netty_LiLinFeng/tree/master/nettybook2-master/src/com/phei/netty/protocol/netty/codec
 * @date 2020-10-04 17:22
 **/
public class NettyMessage {

    private Header header;

    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
