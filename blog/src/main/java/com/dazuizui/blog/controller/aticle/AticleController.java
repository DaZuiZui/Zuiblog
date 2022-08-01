package com.dazuizui.blog.controller.aticle;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.blog.serivce.article.ArticleServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author YiDa Yang
 * @time   21-7-2021 14:31
 * 博文基本操作的控制器
 */

@RestController
public class AticleController {
    @Autowired
    private ArticleServer articleServer;

    /**
     * 获取用户所发布的文章数量
     */
    @GetMapping("/blog/user/getuserarticlenumbers")
    public String getUserArtileNumbers(@RequestParam("author")String author){
        return articleServer.getUserArtileNumbers(author);
    }

    /**
     * 分页查询用户发布的文章
     *
     * @param author
     * @return
     */
    @GetMapping("/blog/user/paingToGetArticleOfUser")
    public String pagingToGetArticleOfUser(@RequestParam("author")String author,@RequestParam("start")int start){
        System.out.println(start);
        return articleServer.pagingToGetArticleOfUser(author,start);
    }

    /**
     * 查询公开博文的数量
     * @return
     */
    @GetMapping("/blog/viewTheNumberOfpublicArticle")
    public String viewTheNumberOfPulbicArticle(){
        return JSONArray.toJSONString(articleServer.viewTheNumberOfPulbicArticle());
    }

    /**
     * 查询用户发布的公开博文
     * Query a user's public blog posts
     */
    @GetMapping("/blog/user/getUsersPublicarticle")
    public String queryAUsersPublicArticle(@RequestParam("author") String author){
        System.err.println("asd");
        return articleServer.queryAUsersPublicArticle(author);
    }


    /**
     * 模糊查询
     */
    @GetMapping("/blog/fuzzyquery")
    public String queryFuzzyQuery(@RequestParam("title") String title){

        return articleServer.queryFuzzyQuery(title);
    }

    /**
     * 通过author和title获取指定文章
     */
    @PostMapping("/blog/user/queryArticleByUsernameAndTitleOUT")
    public String queryArticleByUsernameAndTitleOUT(@RequestParam("token") String token,@RequestParam("title") String title){
        return articleServer.queryArticleByUsernameAndTitle(token,title);
    }

    /**
     * 修改自己发布的某一个文章内容
     * @param token         用户密钥
     * @param title         标题
     * @param author        作者
     * @param technical     用户分类
     * @param id            文章主键
     * @return
     */
    @PostMapping("/blog/user/queryArticleByUsernameAndTitle")
    public String queryArticleByUsernameAndTitle(@RequestParam("token")String token,@RequestParam("title") String title,@RequestParam("author") String author,@RequestParam("technical") String technical,@RequestParam("id") Integer id){
        return articleServer.getsTheSpecifiedArticle(title,author,technical,id);
    }

    /**
     * 获取指定文章
     * @param title
     * @param author
     * @param technical
     * @return
     */
    @PostMapping("/blog/user/getTheSpecifiedArticle")
    public String getsTheSpecifiedArticle(@RequestParam("title") String title,@RequestParam("author") String author,@RequestParam("technical") String technical,@RequestParam("id")Integer id){
        return articleServer.getsTheSpecifiedArticle(title,author,technical,id);
    }

    /**
     * 获取自己发布的全部文章
     */
    @PostMapping("/blog/user/getAllArticle")
    public String getAllArticle(@RequestParam("author") String author){
        return articleServer.getAllArticle(author);
    }

    /**
     * 获取自己发布的文件夹
     * @param author
     * @return
     */
    @PostMapping("/blog/user/getAllFolders")
    public String getAllFolders(@RequestParam("author")String author){
        return articleServer.getAllFolders(author);
    }

    /**
     * 获取指定文件夹分类中的数据
     */
    @PostMapping("/blog/user/getTheFilesData")
    public String getTheFilesData(@RequestParam("token") String token , @RequestParam("blogfileclass") String blogfileclas){
        return articleServer.getTheFilesData(token,blogfileclas);
    }


    /**
     * 分页查询数据
     * @param start  开始位置
     * @return  String.class
     */
    @GetMapping("/blog/getPaingQueryDate")
    public String PagingQueryDate(@RequestParam("start") int start){
        return articleServer.PagingQueryDate(start);
    }

    /**
     * 获取一些展示数据
     * @return
     */
    @GetMapping("/blog/getSomeAricle")
    public String getSomeArticle(){
        return articleServer.getSomeArticle();
    }
}
