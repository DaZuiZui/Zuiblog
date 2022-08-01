package com.dazuizui.blog.serivce.blogging;

import com.dazuizui.api.pojo.Article;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 博客创作控制台
 * Blogging Business implementation
 */
@Service
public interface BloggingServer {

    /**
     * 用户修改信息后调用的改接口用来修改博文的author
     * @param token
     * @param name
     * @return
     */
    public String updateNameOfArticle(String token,String name);
    /**
     * 修改博文的name
     */
    @PostMapping("/user/admin/updateBlogAricleName")
    public String updateBlogAritcleName(@RequestParam("oldname")String oldname,@RequestParam("newname")String newname);

        /**
         * 管理员删除一个博文
         * @param username     被删除用户的username
         * @param title        标题
         * @param token        管理员token
         * @return
         */
    @PostMapping("/blog/admin/deleteTheArticle")
    public String admindeleteTheArticle(@RequestParam("username") String username,@RequestParam("title") String title,@RequestParam("token") String token);

    /**
     * 修改博文
     */
    @PostMapping("/blog/user/updateTheArticle")
    public String updateTheArticle(@RequestParam("token")String token,@RequestParam("title") String title,@RequestBody Article article);
    /**
     * 添加博文
     * @param article
     * @return
     */
    public String WritingABlog(Article article);

    /**
     * 管理员修改文章
     * @param token
     * @param title
     * @param article
     * @return
     */
    public String adminupdateTheArticle(@RequestParam("token")String token,@RequestParam("title") String title,@RequestBody Article article,@RequestParam("author") String author);
        /**
         *  删除指定文件夹
         */
    public String deleteTheFolders(@RequestParam("author") String author,@RequestParam("blogfileclass")String blogfileclass);

    /**
     *  删除指定文章
     */
    public String deleteTheArticle(String token,String title);
}

