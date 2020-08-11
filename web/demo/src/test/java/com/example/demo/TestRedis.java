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

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Semaphore;

public class TestRedis {


    @Test
    public void testLua(){
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
