package com.dazuizui.users.redis.admin.adminOperitions.impl;

import com.dazuizui.api.pojo.Article;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.redis.admin.adminOperitions.AdminOperitionsRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class AdminOperitionsRedisImpl implements AdminOperitionsRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 模糊查询用户信息列表
     * @param username
     * @return
     */
    @Override
    public ArrayList<User> fuzzyQueryUserInfoByUsername( String username) {
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("dazuiblog:user:information:map",
                ScanOptions.scanOptions().match("*"+username+"*").count(1000).build());
        ArrayList<User> arrayList = new ArrayList<>();

        //获取模糊查询出来的数据 in redis
        while (cursor.hasNext()) {
            Map.Entry<Object,Object> entry = cursor.next();
            //Object key = entry.getKey();
            User user = (User) entry.getValue();
            arrayList.add(user);
        }

        //关闭cursor
        try {
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }


}
