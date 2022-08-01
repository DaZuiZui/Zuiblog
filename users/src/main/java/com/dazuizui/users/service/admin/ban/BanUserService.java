package com.dazuizui.users.service.admin.ban;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public interface BanUserService {
    /**
     * 封禁用户
     */
    public String banUser(@RequestParam("token")String token, @RequestParam("beBanUsername")String beBanUsername);
}
