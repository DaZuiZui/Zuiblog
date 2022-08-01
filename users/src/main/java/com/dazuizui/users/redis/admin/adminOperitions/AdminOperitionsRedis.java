package com.dazuizui.users.redis.admin.adminOperitions;

import com.dazuizui.api.pojo.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface AdminOperitionsRedis {
    /**
     * 模糊查询用户信息列表
     * @param username
     * @return
     */
    public ArrayList<User> fuzzyQueryUserInfoByUsername(  String username);
}
