package com.dazuizui.users.redis.admin.delete;

import com.dazuizui.api.pojo.Article;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface DeleteUserRedis {
    /**
     * 清除用户在这个服务器的所有信息
     * @param username
     */
    public void delteUserAllInfo(String username);

    /**
     *  删除用户文章
     *  Article.class 需要有.
     *          Technical
     *          Privacy
     *          Blogfileclass
     */
    public void deleteTheArticle(ArrayList<Article> willDleteArrayList,String author);
}


