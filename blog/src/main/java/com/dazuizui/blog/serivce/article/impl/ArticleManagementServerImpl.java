package com.dazuizui.blog.serivce.article.impl;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.pojo.Article;
import com.dazuizui.blog.mapper.article.ArticleManagementMapper;
import com.dazuizui.blog.mapper.blogging.BlogingMapper;
import com.dazuizui.blog.redis.Article.ArticleRedis;
import com.dazuizui.blog.redis.blogging.BloggingRedis;
import com.dazuizui.blog.serivce.article.ArticleManagementServer;
import com.dazuizui.blog.utilitys.users.UserInfoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class ArticleManagementServerImpl implements ArticleManagementServer {
    @Autowired
    private UserInfoTool userInfoTool;      //工具类
    @Autowired
    private ArticleRedis articleRedis;      //文章redis数据库
    @Autowired
    private ArticleManagementMapper articleManagementMapper;
    @Autowired
    private BloggingRedis bloggingRedis;        //创作redis
    @Autowired
    private BlogingMapper blogingMapper;

    /**
     * 管理员获取用户发布的全部文章
     *
     * 此方法out
     */
    @Override
    public String adminGetAllPublicArticle(String token) {
        //解析token
        boolean b = userInfoTool.DetermineWhetherTheAdministratorIsAnAdministratorByToken(token);
        if (b){
            //查询redis数据
            ArrayList<Article> articleArray = articleRedis.getPublicArticleDataToRedisList();

            /**
             * 非空判断如果为空就去mysql查询
             */
            if (articleArray.size() == 10000){
                articleArray = articleManagementMapper.adminQueryArticle();

                //如果依然非空 数据同步到数据库
                if (articleArray != null){
                    //将数据添加到redis
                    for (int i = 0; i < articleArray.size(); i++) {
                        bloggingRedis.addPublicDataToRedisList(articleArray.get(i));
                    }
                }
            }

            //将查询的结果返回给前端
            return JSONArray.toJSONString(articleArray);
        }else {
            return "0x6610";
        }
    }

    /**
     * 移除指定文章
     * @param author    文章作者
     * @param title     标题
     * @param technical 分类
     * @return
     */
    @Override
    @Transactional
    public String removeTheAriticle(String author, String title, String technical) {
        //删除redis
        articleRedis.removeTheArticle(author, title, technical);
        //删除mysql
        blogingMapper.deleteTheArticle(author,title);
        return "删除成功";
    }
}
