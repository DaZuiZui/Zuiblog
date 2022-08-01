package com.dazuizui.users.redis.admin.ban;

import org.springframework.stereotype.Service;

@Service
public interface BanUserRedis {
    /**
     * 添加封禁记录
     */
    public void addBannedUser(String username);
}
