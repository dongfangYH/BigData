package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class TestRedisTemplate {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testPipeline() {
        List<Object> l = stringRedisTemplate.executePipelined((RedisCallback<Long>) redisConnection -> {
            RedisListCommands listCmd = redisConnection.listCommands();
            listCmd.lPush("aaa".getBytes(), "aaa".getBytes());
            listCmd.lPush("bbb".getBytes(), "bbb".getBytes());
            listCmd.lPush("ccc".getBytes(), "ccc".getBytes());
            return null;
        });
        System.out.println(l);
    }

    @Test
    public void testMulti() {
        String key = "test";
        List<Object> result = stringRedisTemplate.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.watch(key.getBytes());
                redisConnection.multi();
                redisConnection.set("asd".getBytes(), "vvv".getBytes());
                redisConnection.set("zxc".getBytes(), "vvv".getBytes());
                return redisConnection.exec();
            }
        });

        System.out.println(result);

    }
}
