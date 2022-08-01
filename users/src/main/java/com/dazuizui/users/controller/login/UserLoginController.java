package com.dazuizui.users.controller.login;

import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.service.login.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jack Yang
 * @Time   2021/7/10
 * 登入接口
 */
@CrossOrigin
@RestController
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;
    /**
     * 用户登入验证
     * @param user
     * @return String.json
     */
    @PostMapping("/user/p/userlogin")
    public String userlogin(@RequestBody User user){
         String jsonString = userLoginService.userlogin(user);
         System.err.println(jsonString);
        return jsonString;
    }


}
