package com.dazuizui.users.controller.login;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.RequestJson;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.service.login.impl.UserRegisterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 杨易达
 * @Text   用户注册账户信息
 */
@CrossOrigin
@RestController
public class UserRegisterController {
    //返回的json字符串啊
    private RequestJson requestJson;
    @Autowired
    private UserRegisterServiceImpl userRegisterService;
    //返回的json字符串
    private String stringJson;

    /**
     * 用户注册
     * @param user
     * @return String.class
     */
    @PostMapping("/user/p/userregister")
    public String registerUser(@RequestBody User user,@RequestParam("token")String token){

        System.out.println("注册信息"+user);
        //提交给业务层进行添加操作
        stringJson = this.userRegisterService.addUserinformation(user);
        //返回json字符串
        return stringJson;
    }

    /**
     * 获取非幂性的token
     */
    @PostMapping("/user/reg/getNonPowerTokenString")
    public String getNonPower(){
        return JSONArray.toJSONString(userRegisterService.getNonPower());
    }
}
