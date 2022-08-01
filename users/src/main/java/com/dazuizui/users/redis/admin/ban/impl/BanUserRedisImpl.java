package com.dazuizui.users.redis.admin.ban.impl;

import com.dazuizui.users.redis.admin.ban.BanUserRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class BanUserRedisImpl implements BanUserRedis {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加封禁记录
     */
    public void addBannedUser(String username){
        redisTemplate.opsForValue().set("dazuiblog:banned:"+username,"0",315360000, TimeUnit.SECONDS);
    }
}
