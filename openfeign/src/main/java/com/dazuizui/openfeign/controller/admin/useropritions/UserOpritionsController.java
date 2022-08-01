package com.dazuizui.openfeign.controller.admin.useropritions;

import com.dazuizui.openfeign.service.user.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserOpritionsController {
    @Autowired
    private UserService userService;

    /**
     * 模糊查询用户信息
     */
    @GetMapping("/user/admin/fuzzyQueryUserInfoByUsername")
    public String fuzzyQueryUserInfoByUsername(@RequestParam("token")String token,@RequestParam("username")String username){
        return userService.fuzzyQueryUserInfoByUsername(token,username);
    }

    /**
     * 设置一个管理员
     * @param token
     * @param username
     * @return
     */
    @PostMapping("/user/admin/settingAnAdmin")
    public String settingAnAdmin(@RequestParam("token")String token, @RequestParam("username")String username){
        return userService.settingAnAdmin(token,username);
    }

    /**
     * 删除一个管理员
     * @param token
     * @param username
     * @return
     */
    @PostMapping("/user/admin/deleteAnAdmin")
    public String deleteAnAdmin(@RequestParam("token")String token,@RequestParam("username")String username){
        return userService.deleteAnAdmin(token,username);
    }
}
