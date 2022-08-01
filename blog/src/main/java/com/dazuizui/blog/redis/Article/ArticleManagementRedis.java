package com.dazuizui.blog.redis.Article;

import com.dazuizui.api.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ArticleManagementRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * get all article
     * 获取公开全部文章
     *
     * 此方法out
     */
    public ArrayList<Article> getPublicArticleDataToRedisList(){
        return (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:public",0,-1);
    }
}
