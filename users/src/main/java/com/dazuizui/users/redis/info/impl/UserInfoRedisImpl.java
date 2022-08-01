package com.dazuizui.users.redis.info.impl;

import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.redis.info.UserInfoRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserInfoRedisImpl implements UserInfoRedis {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * redisTemplate.opsForList().range("dazuiblog:user:information",0,-1);
     * 修改用户列表中的数据
     */
    @Override
    public void updateDataInRedisUserList(String username,String author){
        ArrayList<User> arrayList = (ArrayList<User>) redisTemplate.opsForList().range("dazuiblog:user:information",0,-1);

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getUsername().equals(username)){
                //更新user数据
                User user = arrayList.get(i);
                user.setName(author);
                //写入redis
                redisTemplate.opsForList().set("dazuiblog:user:information",i,user);
                break;
            }
        }

        return ;
    }

    /**
     * 用户信息map集合 by username
     * @param username
     */
    @Override
    public User getUserInformationMap(String username) {
        return (User) redisTemplate.opsForHash().get("dazuiblog:user:information:map",username);
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
     * 获取全部用户信息
     */
    @Override
    public ArrayList<User> getUserInformationList() {
        return (ArrayList<User>) redisTemplate.opsForList().range("dazuiblog:user:information",0,-1);
    }

    /**
     * 用户信息添加list集合
     */
    @Override
    public void settingAnAdmin(User user) {
        ArrayList<User> userInformationList = getUserInformationList();
        for (int i = 0; i < userInformationList.size(); i++) {
            if (userInformationList.get(i).getUsername().equals(user.getUsername())) {
                redisTemplate.opsForList().remove("dazuiblog:user:information",i,userInformationList.get(i));

                break;
            }
        }

        redisTemplate.opsForList().leftPush("dazuiblog:user:information",user);
    }

    /**
     * 查询用户information
     */
    @Override
    public User selectUserInformation(String username) {
        User user = (User) redisTemplate.opsForValue().get("dazuiblog:user:"+username+":information");
        return user;
    }

    /**
     * 将用户信息保存到redis list集合
     * 该方法使用前提redis该间的数据为null
     */
    @Override
    public void addUserDatatoRedisList(User user){
        redisTemplate.opsForList().leftPush("dazuiblog:user:information",user);
    }

    /**
     * 用户信息添加到redis缓存中
     */
    @Override
    public void addUserInformation(User user) {
        redisTemplate.opsForValue().set("dazuiblog:user:"+user.getUsername()+":information",user);
    }

    /**
     * 用户信息添加到redis缓存中
     */
    @Override
    public User getUserInformation(String username) {
        return (User) redisTemplate.opsForValue().get("dazuiblog:user:"+username+":information");
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @Override
    public ArrayList<User> getUserInfoList() {
        return (ArrayList<User>) redisTemplate.opsForList().range("dazuiblog:user:information",0,-1);
    }
}