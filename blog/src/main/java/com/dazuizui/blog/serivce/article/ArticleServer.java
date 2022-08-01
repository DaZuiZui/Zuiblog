package com.dazuizui.blog.serivce.article;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public interface ArticleServer {

    /**
     * 分页查询用户发布的文章
     * @param author
     * @return
     */
    @PostMapping("/blog/user/paingToGetArticleOfUser")
    public String pagingToGetArticleOfUser(@RequestParam("author")String author,int start);

    /**
     * 获取用户所发布的文章数量
     */
    @GetMapping("/blog/user/getuserarticlenumbers")
    public String getUserArtileNumbers(@RequestParam("author")String author);

    /**
     * 查询公开博文的数量
     */
    public long viewTheNumberOfPulbicArticle();

    /**
     * 分页查询数据
     * @param start  开始位置
     * @return  String.class
     */
    public String PagingQueryDate(@RequestParam("start") int start);

    /**
     * 查询用户发布的公开博文
     * Query a user's public blog posts
     */
    public String queryAUsersPublicArticle(@RequestParam("author") String author);

    /**
     * 模糊查询
     */
    @GetMapping("/blog/fuzzyquery")
    public String queryFuzzyQuery(@RequestParam("title") String title);

    /**
     * 通过author和title获取指定文章
     */
    @PostMapping("/blog/user/queryArticleByUsernameAndTitle")
    public String queryArticleByUsernameAndTitle(String token,String title);

    /**
     * 获取置顶文章
     * @param title
     * @param username
     * @param technical
     * @return
     */
    public String getsTheSpecifiedArticle(String title,String username,String technical,Integer id);


    /**
     * 获取自己发布的全部文章
     */
    public String getAllArticle(@RequestParam("username") String username);

    /**
     * get some article
     * @return
     */
    public String getSomeArticle();

    /**
     * 查看自己创建的文件夹
     */
    public String getAllFolders(@RequestParam("author")String author);

    /**
     * 获取指定文件夹分类
     */
    public String getTheFilesData(@RequestParam("token") String token ,@RequestParam("blogfileclass") String blogfileclas);


}
