package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import javax.annotation.Resource;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestRedis {

    @Test
    public void testDealyQueue() throws Exception {

        URLClassLoader loader = new URLClassLoader(new URL[]{new URL("file:/soft/jdk/jre/lib/rt.jar")}, null);

        Class clazz = loader.loadClass("java.lang.String");
        System.out.println(clazz);

        System.out.println(clazz.getClassLoader());
        System.out.println(System.getProperty("java.class.path"));
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String zkey = "delay_queue";
        Map<String, Double> menbers = new HashMap<>();
        menbers.put("msg-1", Double.valueOf(System.currentTimeMillis() + 10000l));
        menbers.put("msg-2", Double.valueOf(System.currentTimeMillis() + 20000l));
        menbers.put("msg-3", Double.valueOf(System.currentTimeMillis()));
        //jedis.zadd(zkey, menbers);


        Set<Tuple> tuples = jedis.zrevrangeByScoreWithScores(zkey, Double.valueOf(System.currentTimeMillis()), 0d);
        List<String> msgs = tuples.stream().map(Tuple::getElement).collect(Collectors.toList());

        msgs.stream().forEach(e -> {
            System.out.println(e);
            jedis.zrem(zkey, e);
        });

    }


    @Test
    public void testLua() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        StringBuilder scriptBuilder = new StringBuilder();
        scriptBuilder.append("if (redis.call('exists', KEYS[1]) == 0) then ")
                .append("redis.call('hset', KEYS[1], ARGV[2], 1); ")
                .append("redis.call('pexpire', KEYS[1], ARGV[1]); ")
                .append("return nil; ")
                .append("end; ")
                .append("if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then ")
                .append("redis.call('hincrby', KEYS[1], ARGV[2], 1); ")
                .append("redis.call('pexpire', KEYS[1], ARGV[1]); ")
                .append("return nil; ")
                .append("end; ")
                .append("return redis.call('pttl', KEYS[1]); ");
        String script = scriptBuilder.toString();
        System.out.println(script);
        Object result = jedis.eval(script, 1, "aaa", "aaa");
        System.out.println(result);
    }
}
