package com.dazuizui.openfeign.controller.blog.blogging;

import com.dazuizui.api.pojo.Article;
import com.dazuizui.openfeign.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BloggingController {
    @Autowired
    private BlogService blogServer;
    /**
     * 修改信息完成后 去博文里更改用户的用户名
     * @param token
     * @param name
     * @return
     */
    @PostMapping("/blog/user/pr/updateNameOfArticle")
    public String updateNameOfArticle(@RequestParam("token")String token,@RequestParam("name")String name){

        return blogServer.updateNameOfArticle(token,name);
    }

    /**
     * 修改博文的name
     */
    @PostMapping("/user/admin/updateBlogAricleName")
    public String updateBlogAritcleName(@RequestParam("oldname")String oldname,@RequestParam("newname")String newname){
        return blogServer.updateBlogAritcleName(oldname, newname);
    }
    @PostMapping("/blog/admin/deleteTheArticle")
    public String admindeleteTheArticle(@RequestParam("username") String username,@RequestParam("title") String title,@RequestParam("token") String token){
        return blogServer.admindeleteTheArticle(username, title, token);
    }

    /**
     * 写博客
     * @param article
     * @return
     */
    @PostMapping("/blog/user/addAnArticle")
    public String WritingABlog(@RequestParam("Idemtoken")String Idemtoken,@RequestBody Article article){
        return blogServer.WritingABlog(Idemtoken,article);
    }

    /**
     * 获取一些展示文章
     * @return
     */
    @GetMapping("/blog/getSomeAricle")
    public String getSomeArticle(){
        return blogServer.getSomeArticle();
    }

    /**
     * 删除文件夹
     * @param author                删除的用户名
     * @param blogfileclass         删除的文件夹
     * @return
     */
    @PostMapping("/blog/user/deleteTheFolders")
    public String deleteTheFolders(@RequestParam("author") String author,@RequestParam("blogfileclass")String blogfileclass){
        return blogServer.deleteTheFolders(author,blogfileclass);
    }

    /**
     * 用户删除指定文章
     * @param token 用户token
     * @param title 删除的标题
     * @return
     */
    @PostMapping("blog/user/deleteTheArticle")
    public String deleteTheArticle(@RequestParam("token")String token,@RequestParam("title")String title){
        return blogServer.deleteTheArticle(token,title);
    }

    /**
     * 用户修改文章
     * @param token         用户token
     * @param title         标题
     * @param article       被修改为的内容
     * @return
     */
    @PostMapping("/blog/user/updateTheArticle")
    public String updateTheArticle(@RequestParam("Idemtoken")String Idemtoken,@RequestParam("token")String token,@RequestParam("title") String title,@RequestParam("privacy")String privacy,@RequestParam("technical")String technical,@RequestBody Article article){
        return blogServer.updateTheArticle(Idemtoken,token,title,privacy,technical,article);
    }

}
