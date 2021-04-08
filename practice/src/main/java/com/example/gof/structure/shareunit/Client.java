package com.example.gof.structure.shareunit;

import java.util.UUID;

public class Client {

    public static void main(String[] args){
        String id = UUID.randomUUID().toString();
        String title = "test";
        String content = "this is content";
        String ip = "10.93.12.98";
        String country = "US";

        Post post = new Post(id, title, content, ip, country);

        System.out.println(post.getIp());
        System.out.println(post.getCountry());

    }
}
