package com.dazuizui.openfeign.controller.admin.user;

import com.dazuizui.openfeign.service.user.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserBanController {
    @Autowired
    private UserService userService;
    /**
     * 封禁用户
     */

    @PostMapping("/user/admin/banned")
    public String banUser(@RequestParam("token")String token, @RequestParam("beBanUsername")String beBanUsername){
        return userService.banUser(token,beBanUsername);
    }
}
