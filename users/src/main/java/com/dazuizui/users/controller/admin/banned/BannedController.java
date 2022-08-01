package com.dazuizui.users.controller.admin.banned;

import com.dazuizui.users.service.admin.ban.BanUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户封禁控制器
 */
@RestController
public class BannedController {

    @Autowired
    private BanUserService banUserService;

    /**
     * 封禁用户
     */
    @PostMapping("/user/admin/banned")
    public String banUser(@RequestParam("token")String token,@RequestParam("beBanUsername")String beBanUsername){
        return banUserService.banUser(token,beBanUsername);
    }
}

