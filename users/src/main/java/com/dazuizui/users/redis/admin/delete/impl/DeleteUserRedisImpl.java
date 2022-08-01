package com.dazuizui.users.redis.admin.delete.impl;

import com.dazuizui.api.pojo.Article;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.redis.admin.delete.DeleteUserRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DeleteUserRedisImpl implements DeleteUserRedis {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 清除用户在这个服务器的所有信息
     * @param username
     */
    @Override
    public void delteUserAllInfo(String username){
        //清除登入信息
        redisTemplate.delete("dazuiblog:user:"+username+":sign");
        //清除地基用户基础
        redisTemplate.delete("dazuiblog:user:"+username);
        //清除用户信息
        redisTemplate.delete("dazuiblog:user:"+username+":information");
        //删除封禁
        redisTemplate.delete("dazuiblog:banned:"+username);
        //删除官方list集合
        ArrayList<User> arrayList = (ArrayList<User>) redisTemplate.opsForList().range("dazuiblog:user:information",0,-1);
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getUsername().equals(username)){
                System.err.println("发现");
                redisTemplate.opsForList().remove("dazuiblog:user:information",i,arrayList.get(i));
            }
        }

        /**
         * 删除官方map集合
         */
        redisTemplate.opsForHash().delete("dazuiblog:user:information:map",username);
    }

    /**
     *  删除用户文章
     *  Article.class 需要有.
     *          Technical
     *          Privacy
     *          Blogfileclass
     */
    @Override
    public void deleteTheArticle(ArrayList<Article> willDleteArrayList,String author) {
        String first = "";      //第一次执行的状态 公开或者私有

        for (int i = 0; i < willDleteArrayList.size(); i++) {
            /**
             * 删除官方分类
             */
            //获取官方分类list集合所有数据
            ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:blog:article:"+willDleteArrayList.get(i).getTechnical()+":"+willDleteArrayList.get(i).getPrivacy(),0,-1);
            /**
             * 找到指定删除的文章删除
             * !first.equals(willDleteArrayList.get(i).getPrivacy());  确保不重复执行 因为遍历一次就可以删除所有公开的数据 如果下一次是私有就再次执行
             */
            for (int j = 0; j < arrayList.size() && !first.equals(willDleteArrayList.get(i).getPrivacy()); j++) {
                if (arrayList.get(j).getTechnical().equals(willDleteArrayList.get(j).getTechnical()) && arrayList.get(i).getAuthor().equals(author)){
                    redisTemplate.opsForList().remove("dazuiblog:blog:article:"+arrayList.get(j).getTechnical()+":"+arrayList.get(j).getPrivacy(),j,arrayList.get(j));
                }
                //更改状态
                first = willDleteArrayList.get(i).getPrivacy();
            }

            /**
             * 删除个人map集合
             */
            redisTemplate.delete("dazuiblog:blog:article:"+author+":Public:"+willDleteArrayList.get(i).getTechnical());
      }

        /**
         * 删除公开或者私有数据in 官方list集合
         */
        //todo 如果公开或者私有数据贡献为0 则不执行
        //删除公开
        ArrayList<Article> publiclyData = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Public", 0, -1);
        for (int i = 0; i < publiclyData.size(); i++) {
            if (publiclyData.get(i).getAuthor().equals(author)){
                redisTemplate.opsForList().remove("dazuiblog:articlelist:Public",i,publiclyData.get(i));
            }
        }
        //删除私有
        ArrayList<Article> privatelyData = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Private", 0, -1);
        for (int i = 0; i < privatelyData.size(); i++) {
            if (privatelyData.get(i).getAuthor().equals(author)){
                redisTemplate.opsForList().remove("dazuiblog:articlelist:Private",i,privatelyData.get(i));
            }
        }

        /**
         * 删除个人用户list集合
         */
        redisTemplate.delete("dazuiblog:blog:articlelist:"+author);
        /**
         * 删除个人文件夹内的文章
         */
        redisTemplate.delete("dazuiblog:blog:article:userfile:"+author);
        /**
         * 删除官方map集合
         */
        redisTemplate.delete("dazuiblog:blog:article:userfile:" +author);

    }
}


