package com.dazuizui.users.redis.admin.blogroll.impl;

import com.dazuizui.api.pojo.Blogroll;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.redis.admin.blogroll.BlogrollRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class BlogrollRedisImpl implements BlogrollRedis {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 存放友情链接
     * Write blogroll date to redis
     * @param blogroll
     */
    @Override
    public void addBlogroll(Blogroll blogroll){
        System.err.println(Integer.toHexString(blogroll.hashCode()));
        redisTemplate.opsForHash().put("dazuiblog:blogroll",blogroll.getName(),blogroll);
    }

    @Override
    public void deleteBlogroll(String name){
        redisTemplate.opsForHash().delete("dazuiblog:blogroll",name);
    }


    /**
     * 模糊查询友情链接
     */
    public ArrayList<Blogroll> fuzzyQueryBlogroll(String title){
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("dazuiblog:user:information:map",
                ScanOptions.scanOptions().match("*"+title+"*").count(1000).build());
        ArrayList<Blogroll> arrayList = new ArrayList<>();

        //获取模糊查询出来的数据
        while (cursor.hasNext()) {
            Map.Entry<Object,Object> entry = cursor.next();
            //Object key = entry.getKey();
            Blogroll blogroll = (Blogroll) entry.getValue();
            arrayList.add(blogroll);
        }

        //关闭cursor
        try {
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * get all blogroll by redis
     * @return
     */
    @Override
    public ArrayList<Blogroll> getBlogroll() {
        ArrayList<Blogroll> list = (ArrayList<Blogroll>)redisTemplate.opsForHash().values("dazuiblog:blogroll");
        return list;
    }

    /**
     * delete a blogroll
     */
    @Override
    public void deleteBlogrollById(String name) {
        redisTemplate.opsForHash().delete("dazuiblog:blogroll",name);
    }

    /**
     * 查询指定友情链接
     * @param name
     * @return
     */
    @Override
    public Blogroll queryTheSpecifiedBlogroll(String name) {
        Blogroll blogroll = (Blogroll) redisTemplate.opsForHash().get("dazuiblog:blogroll",name);

        return blogroll;
    }

    /**
     * update the blogroll
     * @param blogroll
     * @return
     */
    @Override
    public void updateTheBlogroll(Blogroll blogroll) {
        redisTemplate.opsForHash().put("dazuiblog:blogroll",blogroll.getName(),blogroll);
        return ;
    }

    public void deleteAllDate(){
        redisTemplate.delete("dazuiblog:blogroll");
    }
}
