package com.dazuizui.openfeign.controller.user.login;


import com.dazuizui.api.pojo.User;
import com.dazuizui.openfeign.service.user.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 杨易达
 * @Text   用户注册账户信息
 */
@CrossOrigin
@RestController
public class UserRegisterController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param user
     * @return String.class
     */
    @PostMapping("/user/p/userregister")
    public String registerUser(@RequestBody User user,@RequestParam("token")String token){
        return this.userService.registerUser(user,token);
    }

    /**
     * 获取非幂性的token
     */
    @PostMapping("/user/reg/getNonPowerTokenString")
    public String getNonPower(){
        return this.userService.getNonPower();
    }
}
