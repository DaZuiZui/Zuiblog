package com.dazuizui.users.service.userinfo.impl;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.RequestJson;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.mapper.info.UserInfoMapper;
import com.dazuizui.users.redis.info.UserInfoRedis;
import com.dazuizui.users.service.userinfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Jack Yang
 * @Time 2021/7/21 6:37
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRedis userInfoRedis;        //redis数据库
    @Autowired
    private UserInfoMapper userInfoMapper;      //Mysql数据库

    /**
     * updateImageDataInDb
     * @param username
     * @return
     */
    @Override
    @Transactional
    public String updateImageDataInDb(String username) {
        //useranme not is null
        if (username != null) {
            //get the user`s data
            //to redis
            User userInformation = userInfoRedis.getUserInformation(username);
            userInformation.setProfilephotourl("https://dazuiblog.oss-cn-hangzhou.aliyuncs.com/users/"+username+"/headImage/headImage.jpg");
            userInfoRedis.addUserInformationMap(userInformation);
            userInfoRedis.addUserInformation(userInformation);
            //save to db
            userInfoMapper.updateTheUserProfilephotourl(userInformation);
        }

        return "修改成功";
    }

    /**
     * 通过userrname查询name
     * @param username
     * @return
     */
    @Override
    public String getNameByUsername(@RequestParam("username") String username){
        //非控判断
        if (username!=null) {
            //查询redis
            User user = userInfoRedis.getUserInformation(username);
            //go mysql select if reids response is null
            if (user == null){
                HashMap<String,String> map = new HashMap<>();
                map.put("username",username);
                user = userInfoMapper.selectUserInformation(map);


                System.out.println(user);
                //如果mysql中也没有查询到数据就按照注销处理
                if (user == null){
                    user.setName("该账户已经被注销");
                }else{
                    userInfoRedis.addUserInformation(user);
                }
            }
            return JSONArray.toJSONString(user);
        }

        return "该账户已经被注销";
    }

    /**
     * 通过username查询用户信息
     * @param username
     * @return
     */
    @Override
    public String selectUserinfo(String username) {
        //返回的JSON String
        String returnJsonString = null;
        //Redis查询
        User user =  userInfoRedis.getUserInformation(username);

        //如果redis没有查询到就去mysql查询
        if (user == null){
            //数据库查询准备
            HashMap<String,String> map = new HashMap<>();
            map.put("username",username);
            //开始查询
            user = userInfoMapper.selectUserInformation(map);

            //如果数据库依然没有查询到
            if (user == null){
                //创建失败结果
                RequestJson requestJson = new RequestJson("0x0005","还没有进行登入，该没有进行登入不会叫user强制登入",false);
                returnJsonString = JSONArray.toJSONString(requestJson);
            }else{
                //将数据库查询到的数据保存到redis
                userInfoRedis.addUserInformation(user);
                returnJsonString = JSONArray.toJSONString(user);
            }
        }else{
            //redis查询到数据
            returnJsonString = JSONArray.toJSONString(user);
        }

        System.out.println(returnJsonString);
        return returnJsonString;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    @Transactional
    public String updateUserInfo(User user) {
        if (user != null){
            /**
             * 用户模块
             */
            //修改mysql中的数据
            userInfoMapper.updateTheUserInfo(user);
            userInfoMapper.updateNameOfArticle(user.getUsername(),user.getName());
            //修改redis中的数据
            userInfoRedis.addUserInformation(user);
        }

        return "修改成功";
    }


    /**
     * 获取所有用户信息
     * @param token
     * @return
     */
    @Override
    @Transactional
    public String getUserInfoList(String token) {
        ArrayList<User> userInfoList = new ArrayList<>();
        //查询redis
        if (token != null) {
            //解析jwt
            String username = JwtUtil.analysisJWT(token);
            //查询管理员信息
            User user = userInfoRedis.selectUserInformation(username);
            //如果redis为空那就去mysql查询
            if (user == null){
                HashMap<String,String> map = new HashMap<>();
                map.put("username",username);
                user = userInfoMapper.selectUserInformation(map);

                //如果mysql查询到数据了那么就将数据添加到redis
                if (user != null){
                    userInfoRedis.addUserInformation(user);
                }else {
                    System.out.println("操作有错误");
                    return "操作有误";
                }
            }

            //System.out.println(user.getRole().equals("admin"));

            //判断是否为管理员操作
            if (user.getRole().equals("admin")) {
                //查询redis获取用户信息
                //userInfoList = userInfoRedis.getUserInfoList();
                //System.err.println("查询到的结果" + userInfoList);
                //如果redis没有查询到就去数据库查询
                //if (userInfoList.size() == 0) {
                userInfoList = userInfoMapper.selectUserInforList();
                //如果数据库查询到数据那么就保存到redis
                //System.out.println(userInfoList.size());
                //if (userInfoList.size() > 0) {
                //    for (int i = 0; i < userInfoList.size(); i++) {
                //       userInfoRedis.addUserDatatoRedisList(userInfoList.get(i));
                //    }
                //}
                //}
            }
        }
        //System.err.println(JSONArray.toJSONString(userInfoList));
        return JSONArray.toJSONString(userInfoList);
    }


}
