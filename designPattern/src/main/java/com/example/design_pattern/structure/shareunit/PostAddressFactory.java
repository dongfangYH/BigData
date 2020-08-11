package com.example.design_pattern.structure.shareunit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostAddressFactory {

    private static final Map<String, PostAddress> cache = new ConcurrentHashMap<>();

    public static PostAddress getPostAddress(String ip, String country){
        String key = ip+"::"+country;
        if (!cache.containsKey(key)){
            PostAddress postAddress = new PostAddress(ip, country);
            cache.put(key, postAddress);
        }
        return cache.get(key);
    }

}
