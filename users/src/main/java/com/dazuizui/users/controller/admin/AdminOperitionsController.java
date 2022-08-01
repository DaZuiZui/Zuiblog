package com.dazuizui.users.controller.admin;

import com.dazuizui.users.service.admin.adminOperitions.AdminOperitionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YiDa Yang
 * 管理员基本操作面板
 */
@RestController
public class AdminOperitionsController {
    @Autowired
    private AdminOperitionsService adminOperitionsService;



    /**
     * 模糊查询用户信息
     */
    @GetMapping("/user/admin/fuzzyQueryUserInfoByUsername")
    public String fuzzyQueryUserInfoByUsername(@RequestParam("token")String token,@RequestParam("username")String username){
        return adminOperitionsService.fuzzyQueryUserInfoByUsername(token,username);
    }

    /**
     * 设置一个管理员
     * @param token
     * @param username
     * @return
     */
    @PostMapping("/user/admin/settingAnAdmin")
    public String settingAnAdmin(@RequestParam("token")String token,@RequestParam("username")String username){
        return adminOperitionsService.settingAnAdmin(token,username);
    }

    /**
     * 删除一个管理员
     * @param token
     * @param username
     * @return
     */
    @PostMapping("/user/admin/deleteAnAdmin")
    public String deleteAnAdmin(@RequestParam("token")String token,@RequestParam("username")String username){

        return adminOperitionsService.deleteAnAdmin(token,username);
    }
}
