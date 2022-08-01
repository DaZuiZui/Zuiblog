package com.dazuizui.blog.controller.blogging;

import com.dazuizui.api.pojo.Article;
import com.dazuizui.blog.serivce.blogging.BloggingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 博客创作控制台
 * Blogging Controller
 */
@RestController
public class BloggingController {
    @Autowired
    private BloggingServer bloggingServer;

    /**
     * 修改博文的name
     */
    @PostMapping("/user/admin/updateBlogAricleName")
    public String updateBlogAritcleName(@RequestParam("oldname")String oldname,@RequestParam("newname")String newname){
        return bloggingServer.updateBlogAritcleName(oldname, newname);
    }

    /**
     * 管理员修改官方文档
     * @param token        管理员的token
     * @param title        要修改的博文主题
     * @param article      文章pojo
     * @param author       要修改的文章作者
     * @return
     */
    @PostMapping("/blog/admin/updateArticle")
    public String adminupdateTheArticle(@RequestParam("token")String token,@RequestParam("title") String title,@RequestBody Article article,@RequestParam("author") String author){
        return bloggingServer.adminupdateTheArticle(token,title,article,author);
    }

    //Writing a blog
    @PostMapping("/blog/user/addAnArticle")
    public String WritingABlog(@RequestParam("Idemtoken")String Idemtoken,@RequestBody Article article){
        if (article != null){
            return  bloggingServer.WritingABlog(article);
        }
        return "添加失败，您的数据异常";
    }



    /**
     * 删除文件夹
     */
    @PostMapping("/blog/user/deleteTheFolders")
    public String deleteTheFolders(@RequestParam("author") String author,@RequestParam("blogfileclass")String blogfileclass){
        return bloggingServer.deleteTheFolders(author,blogfileclass);
    }

    /**
     * 用户删除自己的文章
     * @param token        用户的token
     * @param title        文章标题
     * @return
     */
    @PostMapping("blog/user/deleteTheArticle")
    public String deleteTheArticle(@RequestParam("token")String token,@RequestParam("title")String title){
        return bloggingServer.deleteTheArticle(token,title);
    }

    /**
     * 管理员删除一个博文
     * @param username     被删除用户的username
     * @param title        标题
     * @param token        管理员token
     * @return
     */
    @PostMapping("/blog/admin/deleteTheArticle")
    public String admindeleteTheArticle(@RequestParam("username") String username,@RequestParam("title") String title,@RequestParam("token") String token){
        return bloggingServer.admindeleteTheArticle(username,title,token);
    }

    /**
     * 修改博文
     */
    @PostMapping("/blog/user/updateTheArticle")
    public String updateTheArticle(@RequestParam("Idemtoken")String Idemtoken,@RequestParam("token")String token,@RequestParam("title") String title,@RequestParam("privacy")String privacy,@RequestParam("technical")String technical,@RequestBody Article article){
        return bloggingServer.updateTheArticle(token,title,article);
    }


    /**
     * 修改信息完成后 去博文里更改用户的用户名
     * @param token
     * @param name
     * @return
     */
    @PostMapping("/blog/user/pr/updateNameOfArticle")
    public String updateNameOfArticle(@RequestParam("token")String token,@RequestParam("name")String name){
        return bloggingServer.updateNameOfArticle(token,name);
    }
}
