package com.dazuizui.blog.serivce.blogging.impl;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.Article;
import com.dazuizui.blog.mapper.blogging.BlogingMapper;
import com.dazuizui.blog.redis.Article.ArticleRedis;
import com.dazuizui.blog.redis.blogging.BloggingRedis;
import com.dazuizui.blog.serivce.article.ArticleServer;
import com.dazuizui.blog.serivce.article.impl.ArticleServiceImpl;
import com.dazuizui.blog.serivce.blogging.BloggingServer;
import com.dazuizui.blog.utilitys.users.UserInfoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Date;

/**
 * 博客创作控制台
 * Blogging Business implementation
 */
@Service
public class BloggingServerImpl implements BloggingServer {
    @Autowired
    private BloggingRedis bloggingRedis;
    @Autowired
    private BlogingMapper blogingMapper;
    @Autowired
    private UserInfoTool userInfoTool;
    @Autowired
    private ArticleRedis articleRedis;

    /**
     * 修改博文的name
     */
    @Override
    @Transactional
    public String updateBlogAritcleName(@RequestParam("oldname")String oldname,@RequestParam("newname")String newname) {
        //非空判断
        if (oldname != null && newname != null) {
            //获取官方list集合并进行修改
            String username = bloggingRedis.updateNameInAnArticle(oldname, newname);
            //修改MySQL
            blogingMapper.updateNameInBlogArticle(newname,username);
        }
        return "修改成功";
    }

    /**
     * 管理员删除一个博文
     * @param username     被删除用户的username
     * @param title        标题
     * @param token        管理员token
     * @return
     */
    @Transactional
    @Override
    public String admindeleteTheArticle(String username, String title,@RequestParam("token") String token){
        //Invalid judgment
        if (title != null && token != null && username != null){
            //Judge whether it is an admin to operation
            if (userInfoTool.DetermineWhetherTheAdministratorIsAnAdministratorByToken(token)) {
                //删除mysql中的数据
                blogingMapper.deleteTheArticle(username,title);

                /**
                 * 删除redis中的数据
                 */
                //0.删除官方list集合中的数据
                //1.删除数据在官方分类文件夹
                //2.删除个人map集合
                //3.删除个人分类
                //todo 删除官方分类list集合中的数据
                bloggingRedis.removelistdatainredis(username,title);
                //4.删除官方map结婚中的数据
                bloggingRedis.removeAnArticleToRedis(username,title);
                //5.删除个人list结婚
                bloggingRedis.removeDatatoRedisUsernameList(username,title);


                return "删除成功";
            }else{
                return JSONArray.toJSONString("sorry, ur permission is insufficient");
            }
        }
        return null;
    }

    /**
     * 管理员修改官方文档
     * @param token        管理员的token
     * @param title        要修改的博文主题
     * @param article      文章pojo
     * @param author       要修改的文章作者
     * @return
     */
    @Override
    @Transactional
    public String adminupdateTheArticle(@RequestParam("token")String token,@RequestParam("title") String title,@RequestBody Article article,@RequestParam("author") String author){
        //非空判断
        if (token != null && title != null && article != null && author != null){
            if (userInfoTool.DetermineWhetherTheAdministratorIsAnAdministratorByToken(token)) {
                //修改redis

                //修改mysql中的数据
                blogingMapper.updateArticle(article,title);
            }
        }
        return "修改成功";
    }

    /**
     * 用户修改博文
     */
    @Override
    @Transactional
    public String updateTheArticle(@RequestParam("token")String token,@RequestParam("title") String title,@RequestBody Article article){
        //非空判断
        if (token != null && title != null && article != null){
            //修改mysql中的数据
            blogingMapper.updateArticle(article,title);
            blogingMapper.updateArticleDecri(article,title);
            //修改redis
            bloggingRedis.addDatatoRedisByUsername(article);
        }
        return "修改成功";
    }

    /**
     * Add an article to db
     * @param article
     * @return
     */
    @Override
    @Transactional
    public String WritingABlog(Article article) {
        //查询redis中是否存在
        Article tmp = articleRedis.getDatatoRedisByUsernameAndTitleAndTechical(article.getTitle(),article.getAuthor(),article.getTechnical());
        if (tmp == null){
            //查询mysql是否存在
            tmp = blogingMapper.queryDatatoRedisByUsernameAndTitleAndTechical(article);
            //如果redis和mysql都没有查询到数据，那么插入数据到数据库
            if (tmp == null){
                //添加到查询表中
                article.setCreatingTime(new Date());
                article.setUpdateTime(new Date());
                blogingMapper.addDataToMysql(article);
                blogingMapper.addShowDataToMysql(article);
                //添加到redis
                bloggingRedis.addDatatoRedisByUsername(article);
                //redis博文数量+1
                bloggingRedis.countOfAriticle();
                return "添加成功";
            }
        }

        return "添加失败";
    }



    /**
     * 删除指定文件夹
     * @param author
     * @param blogfileclass
     * @return
     */
    @Override
    public String deleteTheFolders(@RequestParam("author") String author,@RequestParam("blogfileclass")String blogfileclass){
        //非空判断
        if (author != null && blogfileclass != null){
            //删除mysql中的数据
            blogingMapper.deleteTheFolders(author,blogfileclass);
            //删除redis官方全部数据的map和list集合公开和私有数据
            //删除官方分类map集合
            //删除个人分类map集合
            //删除指定官方分类list集合
            bloggingRedis.deleteTheFolders(author,blogfileclass);
            //删除按个人分类
            bloggingRedis.deleteUserUpArticlesByBlogfileclass(author,blogfileclass);
            //删除个人用户list集合
            bloggingRedis.removeUserDateList(author,blogfileclass);

        }
        return "删除成功";
    }


    /**
     *  删除指定文章
     */
    @Override
    public String deleteTheArticle(String token, String title) {
        //非空判断
        if (token != null && title != null){
            //解析token
            String username = JwtUtil.analysisJWT(token);
            //删除mysql中的数据
            blogingMapper.deleteTheArticle(username,title);

            /**
             * 删除redis中的数据
             */
            //0.删除官方list集合中的数据
            //1.删除数据在官方分类文件夹
            //2.删除个人map集合
            //3.删除个人分类
            bloggingRedis.removelistdatainredis(username,title);
            //4.删除官方map结婚中的数据
            bloggingRedis.removeAnArticleToRedis(username,title);
            //5.删除个人list结婚
            bloggingRedis.removeDatatoRedisUsernameList(username,title);
            return "删除成功";
        }

        return "删除失败";
    }


    /**
     * 用户修改信息后调用的改接口用来修改博文的author
     * @param token
     * @param name
     * @return
     */
    @Override
    public String updateNameOfArticle(String token,String name){
        String username = JwtUtil.analysisJWT(token);
        blogingMapper.updateNameOfArticle(username,name);
        return "修改成功";
    }
}
