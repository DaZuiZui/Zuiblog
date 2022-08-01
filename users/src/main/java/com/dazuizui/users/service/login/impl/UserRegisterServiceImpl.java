package com.dazuizui.users.service.login.impl;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.RequestJson;
import com.dazuizui.api.pojo.User;

import com.dazuizui.users.mapper.login.UserRegosterMapper;
import com.dazuizui.users.redis.login.UserRegisterRedis;
import com.dazuizui.users.service.login.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author 杨易达
 * @Text   用户注册接口实现类
 */
@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    //返回的json字符串
    private RequestJson requestJson = new RequestJson();                //返回响应json字符串
    @Autowired
    private UserRegisterRedis userRegisterRedis;    //Redis
    @Autowired
    private UserRegosterMapper userRegosterMapper;  //Mapper
    @Autowired
    private RedisTemplate redisTemplate;



    /**
     * 注册用户
     * @param user
     * @return RequsetJson.class
     */
    @Override
    public String addUserinformation(User user) {
        //通过按钮
        boolean passButton = false;

        if (user != null){
            user.setProfilephotourl("https://dazuiblog.oss-cn-hangzhou.aliyuncs.com/test1.jpg");
            user.setRole("user");
            /**
             *  查询账号是否存在
             **/
            //查询redis
            User userInformation = userRegisterRedis.getUserInformation(user);
            System.out.println(userInformation);
            System.out.println(userInformation == null);
            //如果没有查询到就前往数据库查询
            if (userInformation == null){
                userInformation =  userRegosterMapper.selectUserbyUsername(user);
                //如果数据库没有查询到
                if (userInformation == null){
                    //开启通过按钮
                    passButton = true;
                }else{
                    //将数据库查询到的数据添加到redis
                    userRegisterRedis.addUser(user);
                    //添加个人信息list集合
                    userRegisterRedis.addUserInformationList(user);
                    //添加个人信息map集合
                    userRegisterRedis.addUserInformationMap(user);
                }
            }else{
                requestJson.setBooleanJson(false);
                requestJson.setId("0x10002");
                requestJson.setErrorinformation("注册失败，失败原因注册的该账户已经存在");
            }

            if(passButton){
                /**
                 *   将注册信息写入redis
                 */
                /** 0.写入redis用户基础表 **/
                userRegisterRedis.addUser(user);
                userRegosterMapper.adduser(user);

                /** 1.写入redis账号密码缓存和数据库 **/
                //1.1写入redis缓存
                userRegisterRedis.addUsernameAndPassword(user);
                //1.2写入mysql
                userRegosterMapper.addUserlogin(user);

                /** 2.写入mysql用户个人信息 **/
                user.setProfilephotourl("https://dazuiblog.oss-cn-hangzhou.aliyuncs.com/test1.jpg");
                //2.1写入redis缓存
                userRegisterRedis.addUserInformation(user);
                userRegisterRedis.addUserInformationList(user);
                userRegisterRedis.addUserInformationMap(user);
                //2.2写入mysql
                userRegosterMapper.addUserInformation(user);

                //建立成功返回响应
                requestJson.setBooleanJson(true);
                requestJson.setId("0x10000");
                requestJson.setErrorinformation("注册成功");
            }else{
                //建立失败响应
                requestJson.setBooleanJson(false);
                requestJson.setId("0x10002");
                requestJson.setErrorinformation("注册失败，失败原因注册的该账户已经存在");
            }

            return JSONArray.toJSONString(requestJson);
        }

        return "注册失败,您的信息错误";
    }

    /**
     * 获取非幂性的token about用户注入
     */
    @Override
    public String getNonPower() {
        String uuid = JwtUtil.creatUUID();
        redisTemplate.opsForValue().set(uuid,"1",60*5, TimeUnit.SECONDS);
        return uuid;
    }
}
