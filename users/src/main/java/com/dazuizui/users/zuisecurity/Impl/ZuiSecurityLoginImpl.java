package com.dazuizui.users.zuisecurity.Impl;


import com.dazuizui.users.zuisecurity.ZuiSecurityLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * Zui Security System
 * 该接口为登入页面提供安全帮助
 */
@Service
public class ZuiSecurityLoginImpl implements ZuiSecurityLogin {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查看用户是否被封禁
     * @param username
     * 如果为0就为封禁状态，如果>0或者为null就为正常状态
     * @return
     */
    @Override
    public boolean checkToUser(String username) {
        boolean userbanned = true;
        //查询是否封禁
        String numberboolean = (String) redisTemplate.opsForValue().get("dazuiblog:banned:"+username);
        System.out.println("REDSIDB"+numberboolean);
        //设置返回信息
        if(numberboolean != null && numberboolean.equals("0")){
            userbanned = false;
        }

        System.out.println("redis="+userbanned);
        return userbanned;
    }
}
