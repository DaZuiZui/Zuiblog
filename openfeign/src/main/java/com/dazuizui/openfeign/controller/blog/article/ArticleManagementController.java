package com.dazuizui.openfeign.controller.blog.article;

import com.dazuizui.openfeign.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleManagementController {
    @Autowired
    private BlogService blogService;

    /**
     * the admin get all article
     * the admin 获取全部文章
     */
    @PostMapping("/blog/user/adminGetAllPublicArticle")
    public String getAllPublicArticle(@RequestParam("token") String token){
        return  blogService.getAllPublicArticle(token);
    }

    /**
     *
     * @param Idemtoken 幂等性
     * @param token     管理员token
     * @param author    文章作者
     * @param title     标题
     * @param technical 分类
     * @return
     */
    @PostMapping("/blog/admin/deletethearticle")
    public String removeTheAriticle(@RequestParam("Idemtoken")String Idemtoken,@RequestParam("token")String token,@RequestParam("author")String author,@RequestParam("title")String title,@RequestParam("technical")String technical){
        return blogService.removeTheAriticle(Idemtoken, token, author, title, technical);
    }
}
