package com.dazuizui.users.controller.admin.delete;

import com.dazuizui.users.service.admin.delete.DeleteUserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteUserController {
    @Autowired
    private DeleteUserSerivce deleteUserSerivce;

    /**
     * 删除用户信息
     * @param token
     * @param username
     * @return
     */
    @PostMapping("/user/admin/deleteuser")
    public String deleteUser(@RequestParam("token") String token,@RequestParam("username") String username){
        return deleteUserSerivce.deleteTheUser(token,username);
    }
}
