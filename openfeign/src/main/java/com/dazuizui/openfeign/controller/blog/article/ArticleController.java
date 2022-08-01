package com.dazuizui.openfeign.controller.blog.article;

import com.dazuizui.api.pojo.Article;
import com.dazuizui.openfeign.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    @Autowired
    private BlogService blogService;

    /**
     * 分页查询用户发布的文章
     * @param author
     * @return
     */
    @GetMapping("/blog/user/paingToGetArticleOfUser")
    public String pagingToGetArticleOfUser(@RequestParam("author")String author,@RequestParam("start")int start){
       return blogService.pagingToGetArticleOfUser(author,start);
    }

    /**
     * 获取用户所发布的文章数量
     */
    @GetMapping("/blog/user/getuserarticlenumbers")
    public String getUserArtileNumbers(@RequestParam("author")String author){
        return blogService.getUserArtileNumbers(author);
    }

    /**
     * 管理员更新博文
     * @param token
     * @param title
     * @param article
     * @param author
     * @return
     */
    @PostMapping("/blog/admin/updateArticle")
    public String adminupdateTheArticle(@RequestParam("token")String token, @RequestParam("title") String title, @RequestBody Article article, @RequestParam("author") String author){
       return blogService.adminupdateTheArticle(token,title,article,author);
    }
    /**
     *  查询公开博文的数量
     * @return
     */
    @GetMapping("/blog/viewTheNumberOfpublicArticle")
    public String viewTheNumberOfPulbicArticle(){
       return blogService.viewTheNumberOfPulbicArticle();
    }

    /**
     * 分页查询数据
     * @param start  开始位置
     * @return  String.class
     */
    @GetMapping("/blog/getPaingQueryDate")
    public String PagingQueryDate(@RequestParam("start") int start){
        return blogService.PagingQueryDate(start);
    }

    /**
     * 查询用户发布的公开博文
     * Query a user's public blog posts
     */
    @GetMapping("/blog/user/getUsersPublicarticle")
    public String queryAUsersPublicArticle(@RequestParam("author") String author){
        return blogService.queryAUsersPublicArticle(author);
    }

    /**
     * 模糊查询
     */
    @GetMapping("/blog/fuzzyquery")
    public String queryFuzzyQuery(@RequestParam("title") String title){
        System.out.println(title);
        return blogService.queryFuzzyQuery(title);
    }

    /**
     * 通过author和title获取指定文章
     */
    @PostMapping("/blog/user/queryArticleByUsernameAndTitle")
    public String queryArticleByUsernameAndTitle(@RequestParam("token")String token,@RequestParam("title") String title,@RequestParam("author") String author,@RequestParam("technical") String technical,@RequestParam("id")Integer id){
        return blogService.queryArticleByUsernameAndTitle(token,title,author,technical,id);
    }

    /**
     * 查询指定文章
     * @param title
     * @param author
     * @param technical
     * @return
     */
    @PostMapping("/blog/user/getTheSpecifiedArticle")
    public String getsTheSpecifiedArticle(@RequestParam("title") String title, @RequestParam("author") String author, @RequestParam("technical") String technical,@RequestParam("id")String id){
        System.out.println("title"+title);
        return blogService.getsTheSpecifiedArticle(title,author,technical,id);
    }

    /**
     * 获取自己发布的全部文章
     */
    @PostMapping("/blog/user/getAllArticle")
    public String getAllArticle(@RequestParam("author") String author){
        return blogService.getAllArticle(author);
    }

    /**
     * 获取自己发布的文件夹
     * @param author
     * @return
     */
    @PostMapping("/blog/user/getAllFolders")
    public String getAllFolders(@RequestParam("author")String author){
        return blogService.getAllFolders(author);
    }

    /**
     * 获取指定文件夹分类中的数据
     */
    @PostMapping("/blog/user/getTheFilesData")
    public String getTheFilesData(@RequestParam("token") String token , @RequestParam("blogfileclass") String blogfileclas){
        return blogService.getTheFilesData(token,blogfileclas);
    }


}
