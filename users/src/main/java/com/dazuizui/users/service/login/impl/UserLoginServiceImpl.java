package com.dazuizui.users.service.login.impl;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.RequestJson;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.mapper.login.UserLoginMapper;
import com.dazuizui.users.redis.login.UserLoginRedis;
import com.dazuizui.users.service.login.UserLoginService;
import com.dazuizui.users.zuisecurity.ZuiSecurityLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jack Yang
 * @Text   用户登入业务实现
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private UserLoginRedis userLoginRedis;
    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private ZuiSecurityLogin zuiSecurityLogin;

    //进入Zui security的button
    private boolean zuisecuritybutton = false;
    //返回的json字符串
    private String returnJsonString;
    /**
     * @param user
     * @return String.json
     */
    @Override
    public String userlogin(User user) {
        System.out.println("用户登入数据"+user);
        /**
         *   0.1查看账号是否存在
         *   TO DO
         */
        //查询redis获取数据
        User dbuser = userLoginRedis.selectRedisUserInformation(user.getUsername());

        //if the redis query results empty,go to mysql
        if (dbuser == null){
            dbuser = userLoginMapper.selectUserbyUsername(user);
            if (dbuser != null){
                //将数据库存放到redis
                userLoginRedis.addRedisUserInformation(dbuser);
            }
        }else{

        }

        //判断最终是否从查询到数据
        if (dbuser != null){
            //判断账号密码是否正确
            if (user.getUsername().equals(dbuser.getUsername()) && user.getPassword().equals(dbuser.getPassword())){
                System.out.println("账号密码正确");
                //账号密码正确
                zuisecuritybutton = true;
            }else{
                System.out.println("账号密码不正确");
                //账号密码不正确
                zuisecuritybutton = false;
                RequestJson requestJson = new RequestJson("0x0003","账号或者密码错误",false);
                //生成返回json字符串
                returnJsonString = JSONArray.toJSONString(requestJson);
            }
        }else{
            //没有查询到账户
            System.out.println("没有此账户");
            zuisecuritybutton = false;
            RequestJson requestJson = new RequestJson("0x0002","您的账号不存在",false);
            //生成返回json字符串
            returnJsonString = JSONArray.toJSONString(requestJson);
        }

        //1.1查看zui security账号状态是否正常
        if(zuisecuritybutton){
            zuisecuritybutton = zuiSecurityLogin.checkToUser(user.getUsername());
        }



        if(zuisecuritybutton){
            //1.2zui security判断账号与上一次登入地区是否一致
            //if (!dbuser.getCstate().equals(user.getCstate())) {
                //TO DO
            //}

            System.out.println("登入成功的信息"+dbuser);
            //创建jwt令牌
            String jwt = JwtUtil.createJWT(dbuser);
            //创建响应信息
            RequestJson requestJson = new RequestJson("0x0000",jwt,true);
            returnJsonString = JSONArray.toJSONString(requestJson);
        }else{
            RequestJson requestJson = new RequestJson("0x0001","为了此账户的安全，我们无法判断出是否是您本人，请前往进行验证",false);
            returnJsonString = JSONArray.toJSONString(requestJson);
        }

        System.err.println("返回数据"+returnJsonString);
        return returnJsonString;
    }
}