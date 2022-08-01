package com.dazuizui.users.service.admin.adminOperitions.impl;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.mapper.admin.adminOperitions.AdminOperitionsMapper;
import com.dazuizui.users.redis.admin.adminOperitions.AdminOperitionsRedis;
import com.dazuizui.users.redis.info.UserInfoRedis;
import com.dazuizui.users.service.admin.adminOperitions.AdminOperitionsService;
import com.dazuizui.users.utilitys.user.UserInfoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/**
 * @author YiDa Yang
 */
@Service
public class AdminOperitionsServiceImpl implements AdminOperitionsService {
    @Autowired
    private UserInfoTool userInfoTool;
    @Autowired
    private AdminOperitionsMapper adminOperitionsMapper;
    @Autowired
    private UserInfoRedis userInfoRedis;
    @Autowired
    private AdminOperitionsRedis adminOperitionsRedis;





    /**
     * 模糊查询用户信息列表
     * @param token
     * @param username
     * @return
     */
    @Override
    public String fuzzyQueryUserInfoByUsername(String token, String username) {
        ArrayList<User> users = new ArrayList<>();
        //非空判断
        if (token != null && username != null) {
            //判断是否为管理员
            if (userInfoTool.DetermineWhetherTheAdministratorIsAnAdministratorByToken(token)) {
                 users = adminOperitionsRedis.fuzzyQueryUserInfoByUsername(username);
                 //todo 去数据库查询
            }
        }
        //查询redis模糊查询
        return JSONArray.toJSONString(users);
    }

    /**
     * 设置管理员
     * @param token
     * @param username
     * @return
     */
    public String settingAnAdmin(@RequestParam("token")String token,@RequestParam("username")String username){
        //非空判断
        if (token!=null && username != null){
             //判断是否为管理员
             if(userInfoTool.DetermineWhetherTheAdministratorIsAnAdministratorByToken(token)){
                 try {
                     /**
                      * 修改redis
                      */
                     //获取要修改的用户数据
                     User user = userInfoRedis.selectUserInformation(username);
                     //修改list集合 因为修改list集合需要匹配数据 所以修改后list后才设置角色为admin
                     userInfoRedis.settingAnAdmin(user);

                     //修改redis中的缓存
                     user.setRole("admin");
                     userInfoRedis.addUserInformation(user);
                     //修改mapj
                     userInfoRedis.addUserInformationMap(user);

                     //修改mysql
                     adminOperitionsMapper.settingRole(username);
                 } catch (Exception e) {
                     //服务器繁忙
                     return "0x5555";
                 }
             }else {
                 //0x5111权限不足
                 return "0x5111";
             }
        }

        return "设置成功";
    }


    /**
     * 删除一个管理员
     * @param token
     * @param username
     * @return
     */
    @Override
    public String deleteAnAdmin(String token, String username) {
        //非空判断
        if (token != null && username != null) {
            //判断是否为管理员
            if(userInfoTool.DetermineWhetherTheAdministratorIsAnAdministratorByToken(token)){
                /**
                 * 修改redis
                 */
                //获取要修改的用户数据
                User user = userInfoRedis.selectUserInformation(username);

                //修改redis中的缓存
                user.setRole("user");
                userInfoRedis.addUserInformation(user);
                //修改mapj
                userInfoRedis.addUserInformationMap(user);

                //修改mysql
                adminOperitionsMapper.settingRole(username);

            }
        }
        return null;
    }
}
