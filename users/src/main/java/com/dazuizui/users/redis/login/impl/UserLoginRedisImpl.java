package com.dazuizui.users.redis.login.impl;

import com.dazuizui.api.pojo.User;
import com.dazuizui.users.redis.login.UserLoginRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Jack Yang
 * @creation_time  2021/7/10 17:13
 * 用户登入redis缓存层
 */
@Service
public class UserLoginRedisImpl implements UserLoginRedis {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Query user information through redis
     * @param username
     * @return
     */
    @Override
    public User selectRedisUserInformation(String username) {
        User user = (User) redisTemplate.opsForValue().get("dazuiblog:user:"+username+":sign");
        return user;
    }

    /**
     * add user information through redis
     * @param user
     * @return
     */
    @Override
    public void addRedisUserInformation(User user) {
        redisTemplate.opsForValue().set("dazuiblog:user:"+user.getUsername()+":sign",user);
    }



}
