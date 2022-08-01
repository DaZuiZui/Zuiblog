package com.dazuizui.users.redis.info;

import com.dazuizui.api.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Service
public interface UserInfoRedis {
    /**
     * 设置一个管理员
     * @return
     */
    public void settingAnAdmin(User user);

    /**
     * 获取所有用户信息集合
     */
    public ArrayList<User> getUserInformationList();
    /**
     * 查询用户information
     */
    public User selectUserInformation(String username);

    /**
     * 通过username获取name
     * @param username
     */
    public User getUserInformationMap(String username);

    /**
     * 将用户信息保存到redis list集合
     * 该方法使用前提redis该间的数据为null
     */
    public void addUserDatatoRedisList(User user);

    /**
     * 用户信息添加到redis缓存中
     */
    public void addUserInformation(User user);


    /**
     * 用户用户信息
     * @param username
     */
    public User getUserInformation(String username);

    /**
     * 用户信息map集合
     * @param user
     */
    public void addUserInformationMap(User user);

    /**
     * 获取所有用户信息
     * @return
     */
    public ArrayList<User> getUserInfoList();

    /**
     *
     * @param username
     * @param author
     */
    public void updateDataInRedisUserList(String username,String author);
}
