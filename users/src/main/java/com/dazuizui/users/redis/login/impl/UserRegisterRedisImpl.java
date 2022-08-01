package com.dazuizui.users.redis.login.impl;

import com.dazuizui.api.pojo.User;
import com.dazuizui.users.redis.login.UserRegisterRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author 杨易达
 * @time   2021/6/22
 * @Text   用户注册Redis实现类
 */
@Service
public class UserRegisterRedisImpl implements UserRegisterRedis {

    @Autowired
    private RedisTemplate redisTemplate;



    /**
     * 获取用户信息判断当前用户是否被注册
     * @return User.class
     */
    @Override
    public User getUserInformation(User users) {
        //查询键内容
        User user = (User) this.redisTemplate.opsForValue().get("dazuiblog:user:"+users.getUsername());
        return user;
    }

    /**
     * 账号密码添加到redis缓存中
     */
    @Override
    public void addUsernameAndPassword(User user){
        redisTemplate.opsForValue().set("dazuiblog:user:"+user.getUsername()+":sign",user);
    }

    /**
     * 用户信息添加到redis缓存中
     */
    @Override
    public void addUserInformation(User user) {
        redisTemplate.opsForValue().set("dazuiblog:user:"+user.getUsername()+":information",user);
    }

    /**
     * 用户信息添加list集合
     */
    @Override
    public void addUserInformationList(User user) {
        redisTemplate.opsForList().leftPush("dazuiblog:user:information",user);
    }

    /**
     * 用户信息添加list集合
     */
    @Override
    public ArrayList<User> getUserInformationList() {
        return (ArrayList<User>) redisTemplate.opsForList().range("dazuiblog:user:information",0,-1);
    }

    /**
     * 用户信息map集合
     * @param user
     */
    @Override
    public void addUserInformationMap(User user) {
        redisTemplate.opsForHash().put("dazuiblog:user:information:map",user.getUsername(),user);
    }



    /**
     * 用户基础添加到redis缓存中
     */
    @Override
    public void addUser(User user) {
        redisTemplate.opsForValue().set("dazuiblog:user:"+user.getUsername(),user);
    }
}
