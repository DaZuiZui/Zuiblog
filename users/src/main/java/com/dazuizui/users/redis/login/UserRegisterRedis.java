package com.dazuizui.users.redis.login;


import com.dazuizui.api.pojo.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author 杨易达
 * @time   2021/6/22
 * @Text   用户注册Redis实现类
 */
@Service
public interface UserRegisterRedis {

    /**
     * 获取用户信息判断当前用户是否被注册
     * @return User.class
     */
    public User getUserInformation(User user);

    /**
     * 添加用户信息到list集合
     * @param user
     */
    public void addUserInformationList(User user);

    /**
     * 添加用户信息到map集合
     * @param user
     */
    public void addUserInformationMap(User user);


    /**
     * 账号密码添加到redis缓存
     */
    public void addUsernameAndPassword(User user);

    /**
     * 用户个人信息添加到Redis缓存
     */
    public void addUserInformation(User user);

    /**
     * 用户基础添加到redis缓存中
     */
    public void addUser(User user);

    /**
     * 获取所有用户信息集合
     */
    public ArrayList<User> getUserInformationList();
}
