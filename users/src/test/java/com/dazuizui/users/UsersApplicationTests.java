package com.dazuizui.users;

import com.dazuizui.users.mapper.admin.delete.DeleteBlogingMapper;
import com.dazuizui.users.service.userinfo.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class UsersApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        //redisTemplate.opsForValue().set("dazuiblog:article:public:count",59);
        int o = (int) redisTemplate.opsForValue().get("dazuiblog:article:public:count");
        System.out.println(o);
    }
}

