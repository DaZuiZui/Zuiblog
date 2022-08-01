package com.dazuizui.blog;

import com.dazuizui.api.pojo.Article;
import com.dazuizui.blog.mapper.article.ArticleMapper;
import com.dazuizui.blog.mapper.blogging.BlogingMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
class BlogApplicationTests {
//@Autowired
//private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    void contextLoads() throws IOException {
        redisTemplate.opsForValue().set("dazuiblog:article:public:count","70");
        //redisTemplate.opsForValue().increment("dazuiblog:article:public:count",1);
        System.out.println(redisTemplate.opsForValue().get("dazuiblog:article:public:count"));
    }

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void test1()  {
        System.out.println(articleMapper.queryArticleById(114));
    }


}
