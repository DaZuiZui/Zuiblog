package com.dazuizui.users.redis.login;

import com.dazuizui.api.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @author Jack Yang
 * @creation_time  2021/7/10 17:13
 * 用户登入redis缓存层
 */
@Service
public interface UserLoginRedis {

    /**
     * Query user information through redis
     * @param username
     * @return
     */
    public User selectRedisUserInformation(String username);

    /**
     * add user information through redis
     * @param user
     * @return
     */
    public void addRedisUserInformation(User user);
}
