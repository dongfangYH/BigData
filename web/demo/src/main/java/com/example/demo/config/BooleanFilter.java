package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-08 13:56
 **/
@Component
public class BooleanFilter implements InitializingBean {

    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String REDIS_KEY_USER_ID = "user_id:";

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Integer> userIds = userService.findAllUserId();
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        for (Integer userId : userIds) {
            valueOperations.setBit(REDIS_KEY_USER_ID, getBitMapOffset(userId), true);
        }
    }

    /**
     * 查看该用户是否存在
     *
     * @param userId
     * @return
     */
    public boolean exist(Integer userId) {
        return stringRedisTemplate.opsForValue().getBit(REDIS_KEY_USER_ID, getBitMapOffset(userId));
    }

    public static final long getBitMapOffset(Integer id) {
        int hashCode = id.hashCode();
        long offset = (hashCode < 0 ? Integer.MAX_VALUE - hashCode : hashCode) % (long) Math.pow(2, 32);
        return offset;
    }

    public void addBitMapOffset(Integer id) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.setBit(REDIS_KEY_USER_ID, getBitMapOffset(id), true);
    }
}
