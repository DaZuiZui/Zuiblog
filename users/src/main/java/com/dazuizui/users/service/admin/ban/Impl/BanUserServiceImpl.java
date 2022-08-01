package com.dazuizui.users.service.admin.ban.Impl;

import com.dazuizui.users.mapper.admin.ban.BanMapper;
import com.dazuizui.users.redis.admin.ban.BanUserRedis;
import com.dazuizui.users.service.admin.ban.BanUserService;
import com.dazuizui.users.utilitys.user.UserInfoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanUserServiceImpl implements BanUserService {

    @Autowired
    private UserInfoTool userInfoTool;
    @Autowired
    private BanMapper banMapper;
    @Autowired
    private BanUserRedis banUserRedis;


    /**
     * 封禁用户
     * @param token
     * @param beBanUsername
     * @return
     */
    @Override
    public String banUser(String token, String beBanUsername) {
        //非空判断
        if (token != null && beBanUsername != null){
            //判断是否为管理员操作
            boolean bl = userInfoTool.DetermineWhetherTheAdministratorIsAnAdministratorByToken(beBanUsername);
            if(bl){
                //写入redis
                //banUserRedis.addBannedUser(beBanUsername);
                //写入mysql
                banMapper.addUserBanned(beBanUsername);
            }else{
                return "您不是管理员无法进行操作";
            }

        }
        return "封禁成功";
    }
}
