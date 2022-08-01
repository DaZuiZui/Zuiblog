package com.dazuizui.blog.utilitys.users;

import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.User;
import com.dazuizui.blog.utilitys.users.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserInfoTool {
    @Autowired
    private  RedisTemplate redisTemplate;
    @Autowired
    private UserInfoMapper userInfoMapper;
    /**
     * 判断是否为管理员，如果是管理员就返回true，如果不是管理员就返回false token解析方法
     * @param token
     * @return
     */
    public  boolean DetermineWhetherTheAdministratorIsAnAdministratorByToken(String token){
        String username = JwtUtil.analysisJWT(token);
        System.err.println(username);
        User user = (User) redisTemplate.opsForValue().get("dazuiblog:user:"+username+":information");
        if (user == null){
            //数据库查询准备
            HashMap<String,String> map = new HashMap<>();
            map.put("username",username);
            //开始查询
            user = userInfoMapper.selectUserInformation(map);

            System.err.println(user != null);
            //如果数据库查询到数据将查询到的数据保存到redis
            if (user != null){
                redisTemplate.opsForValue().set("dazuiblog:user:"+user.getUsername()+":information",user);
            }
        }
        //管理员判断
        if (user != null){

            if (user.getRole().equals("admin")){
                return true;
            }
        }
        return false;
    }
}
