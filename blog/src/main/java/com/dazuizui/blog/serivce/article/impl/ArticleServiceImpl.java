package com.dazuizui.blog.serivce.article.impl;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.Article;
import com.dazuizui.blog.mapper.article.ArticleMapper;
import com.dazuizui.blog.mapper.blogging.BlogingMapper;
import com.dazuizui.blog.redis.Article.ArticleRedis;
import com.dazuizui.blog.redis.blogging.BloggingRedis;
import com.dazuizui.blog.serivce.article.ArticleServer;
import com.dazuizui.blog.utilitys.users.UserInfoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 博文业务类
 */
@Service
public class ArticleServiceImpl implements ArticleServer {
    @Autowired
    private ArticleRedis articleRedis;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private BloggingRedis bloggingRedis;
    @Autowired
    private BlogingMapper blogingMapper;
    /**
     * 分页查询用户发布的文章
     * @param author
     * @return
     */
    @PostMapping("/blog/user/paingToGetArticleOfUser")
    public String pagingToGetArticleOfUser(String author,int start){

        //查询mysql中的数据
        ArrayList<Article> arrayList = articleMapper.pagingToGetArticleOfUser(author, (start - 1), start * 10);
        articleRedis.setarticlesoftheauthor(author,arrayList);
        return JSONArray.toJSONString(arrayList);


    }

    /**
     * 获取用户所发布的文章数量
     */
    public String getUserArtileNumbers(@RequestParam("author")String author){
        if (author == null){
            return JSONArray.toJSONString("0");
        }

        //查询用户发布文章的数量去redistribution
        Long userArticleNumbers = articleRedis.getUserArticleNumbers(author);
        //todo 去mysql中查询数量


        return JSONArray.toJSONString(userArticleNumbers);
    }

    /**
     * 查询公开博文的数量
     */
    @Override
    public long viewTheNumberOfPulbicArticle() {
        long numberOfPulbicArticle =articleRedis.viewTheNumberOfPulbicArticle();


        return numberOfPulbicArticle;
    }

    /**
     * get some article
     * @return
     */
    @Override
    public String getSomeArticle(){

        //Query data to mysql
        ArrayList<Article> allArticle = articleMapper.getAllArticle();

        return JSONArray.toJSONString(allArticle);
    }

    /**
     * 分页查询数据 此方法还要提供返回数据数量
     * @param start  开始位置
     * @return  String.class
     */
    @Override
    @Transactional
    public String PagingQueryDate(int start) {
        //获取所有数据的条数
        long countOfArticle = this.viewTheNumberOfPulbicArticle();
        /**
         * 查看end是否于大雨countOfArticle，如果大于就等于countOfArticle
         *  如果不大于就计算end的位置
         */
        long end = start * 10;
        if (end > countOfArticle){
            end = countOfArticle -10;
        }else{
            long singlenum = countOfArticle %10;
            end -= singlenum;
        }

        //设置分页的其实位置
        System.out.println(start);
        //开始查询
        ArrayList<Article> paginQueryData = articleMapper.pagingQueryBposts(end);
        System.out.println(paginQueryData);
        return JSONArray.toJSONString(paginQueryData);

    }



    /**
     * 查询用户发布的公开博文
     * Query a user's public blog posts
     */
    @Override
    public String queryAUsersPublicArticle(@RequestParam("author") String author){
        ArrayList<Article> arrayList = new ArrayList<>();
        //非空判断
        if (author != null) {
            //查询redis中的数据
            arrayList = articleRedis.getPublicDataOnredisToIt(author);

            //判断redis查询是否为空 就去mysql查询
            if (arrayList.size() < 1) {
                arrayList = articleMapper.queryAUsersPublicArticle(author);

                //将mysql查询到的数据存放到redis
                if (arrayList != null){
                    for (int i = 0; i < arrayList.size(); i++) {
                        articleRedis.addPublicDataOnredisToIt(arrayList.get(i));
                    }
                }
            }
        }
        return JSONArray.toJSONString(arrayList);
    }

    /**
     * 模糊查询文章
     */
    @Override
    public String queryFuzzyQuery(String title) {
        List<Article> arrayList = new ArrayList<>();
        if (title != null){
             //查询数据库
            arrayList = articleMapper.queryFuzzyQuery(title);


        }
        return JSONArray.toJSONString(arrayList);
    }

    /**
     * 通过username和title查询指定文章
     * @param token
     * @param title
     * @return
     */
    @Override
    public String queryArticleByUsernameAndTitle(String token, String title) {
        /*
        Article article =  new Article();
        //非空判断
        if (token!= null && title != null){
            //解析token
            String author = JwtUtil.analysisJWT(token);
            //查询redis
            article = articleRedis.queryAnArticleToRedisByAuthorAndTitle(author, title);
            //如果redis没有查询到就去mysql查询
            if (article == null) {
                article = articleMapper.queryArticleByUsernameAndTitle(author,title);
                //如果mysql不为空就把他存入redis
                bloggingRedis.addAnArticleToRedis(article);
            }
        }*/

        //返回数据
        return JSONArray.toJSONString(null);
    }

    /**
     * 通过username和author和technical获取指定公开文章
     * @param title
     * @param author
     * @param technical
     * @return
     */
    @Override
    public String getsTheSpecifiedArticle(String title, String author ,String technical,Integer id) {
        //Go to Redis for data
        Article article = articleRedis.getDatatoRedisByUsernameAndTitleAndTechical(title, author, technical);
        //Article article = articleRedis.queryDatatoRedisByUsernameAndTitleAndTechical(title,author,technical);
        if (article == null){
            article = articleMapper.queryArticleByUsernameAndTitleAndTechnical(author,title,technical);

            if (article != null){
                bloggingRedis.addDatatoRedisByUsername(article);
            }else{
                return "null";
            }
        }

        return JSONArray.toJSONString(article);
    }


    /**
     * 获取自己发布的全部文章
     *
     */
    @Override
    public String getAllArticle(String author) {
        //从redis中获取数据
        ArrayList<Article> allUserArticle    = articleMapper.pagingToGetArticleOfMyself(author,0,100);

        return JSONArray.toJSONString(allUserArticle);

        /**
         v 0.0.4 淘汰 存在大内存风险
        if (author != null){
            //查询redis
            allUserArticle = articleRedis.getAllUserArticle(author);
            //如果redis中没有数据就去mysql查询数据
            if (allUserArticle.size() < 1) {
                HashMap<String,String> map = new HashMap<>();
                map.put("author",author);
                allUserArticle = articleMapper.getAllArticlebyauthor(map);
                //如果mysql有数据写入redis
                if (allUserArticle != null){
                    //添加到redis
                    for (int i = 0; i < allUserArticle.size(); i++) {
                        bloggingRedis.addDatatoRedisUsernameList(allUserArticle.get(i));
                    }
                }
            }
        }*/
    }



    /**
     * 查看自己创建的文件夹
     */
    @Override
    public String getAllFolders(String author) {
        ArrayList<Article> folders = articleRedis.getfolders(author);
        if (folders == null || folders.size() ==0){
            folders = articleMapper.getAllFolders(author);
            if (folders != null || folders.size() == 0){
                articleRedis.setfolders(author,folders);
            }
        }
        return JSONArray.toJSONString(folders);

        /*
        淘汰代码
        ArrayList<Article> allUserArticle = new ArrayList<>();      //存放博文的list集合
        ArrayList<String>  arrayList = new ArrayList<>();           //存放文件夹的list集合

        if (author != null){
            //查询redis
            arrayList = articleRedis.getAllFolders(author);
            System.err.println(arrayList.size());

            //如果redis中没有查到数据就去mysql
            if (arrayList.size() < 1) {
                HashMap<String,String> map = new HashMap<>();
                map.put("author",author);
                allUserArticle = articleMapper.getAllFolders(map);
                System.err.println(allUserArticle!=null);
                //如果mysql有数据写入redis
                if (allUserArticle != null){
                    //添加到redis
                    for (int i = 0; i < allUserArticle.size(); i++) {
                        bloggingRedis.addBlogfileclassInRedis(allUserArticle.get(i));
                    }
                }
            }
        }*/

        /**
         * 判断是返回redis中的数据还是mysql中的数据 如果arrayList.size() > 0成立就返回redis中的数据
         if (arrayList.size() > 0){
             return JSONArray.toJSONString(arrayList);
         }else{
             return JSONArray.toJSONString(allUserArticle);
        }
         */
    }

    /**
     * 获取指定文件夹的内容
     * @param blogfileclas
     * @return
     */
    @Override
    public String getTheFilesData(String token , String blogfileclas) {
        ArrayList<Article> blogfileclassDataInRedis = new ArrayList<>();
        //非空判断
        if (token != null && blogfileclas != null){
            //解析token
            String username = JwtUtil.analysisJWT(token);
            //查询redis
            blogfileclassDataInRedis = articleRedis.getBlogfileclassDataInRedis(username, blogfileclas);
            //如果redis中没有查询到数据就去mysql中查询
            if (blogfileclassDataInRedis == null) {
                blogfileclassDataInRedis = articleMapper.getBlogfileclassDataInRedis(username, blogfileclas);
                //如果mysql查询到的数据就把他添加到redis中
                if (blogfileclassDataInRedis != null) {
                    for (int i = 0; i < blogfileclassDataInRedis.size(); i++) {
                        bloggingRedis.addBlogfileclassInRedis(blogfileclassDataInRedis.get(i));
                    }
                }
            }
        }

        return JSONArray.toJSONString(blogfileclassDataInRedis);
    }


}
