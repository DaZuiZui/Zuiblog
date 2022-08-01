package com.dazuizui.openfeign.controller.admin.user;

import com.dazuizui.openfeign.service.user.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAdminController {
    @Autowired
    private UserService userService;

    /**
     * 获取所有用户信息
     */
    @PostMapping("/user/admin/getuserInfoList")
    public String getUserInfoList(@RequestParam("token") String token){
        return userService.getUserInfoList(token);
    }

    /**
     * 通过username获取用户信息
     */
    @PostMapping("/user/admin/getuserInfo")
    public String getUserInfor(@RequestParam("username") String username){
        return userService.getUserInfor(username);
    }

    /**
     * 删除一个用户
     * @param token
     * @param username
     * @return
     */
    @PostMapping("/user/admin/deleteuser")
    public String deleteUser(@RequestParam("token") String token,@RequestParam("username") String username){
        return userService.deleteUser(token,username);
    }
}
