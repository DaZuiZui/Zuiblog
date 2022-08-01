package com.dazuizui.openfeign.controller.user.login;

import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.User;
import com.dazuizui.openfeign.service.user.login.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserLoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户登入验证
     * @param user
     * @return String.json
     */
    @PostMapping("/user/p/userlogin")
    @HystrixCommand
    public String userlogin(@RequestBody User user){
        System.out.println(user);
        return  this.userService.userlogin(user);
    }
}
