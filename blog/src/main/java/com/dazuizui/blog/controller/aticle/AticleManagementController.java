package com.dazuizui.blog.controller.aticle;

import com.dazuizui.blog.serivce.article.ArticleManagementServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AticleManagementController {
    @Autowired
    private ArticleManagementServer articleManagementServer;

    /**
     * 管理员获取用户发布的全部文章
     *
     * 此方法out
     */
    @PostMapping("/blog/user/adminGetAllPublicArticle")
    public String getAllPublicArticle(@RequestParam("token") String token){
        return articleManagementServer.adminGetAllPublicArticle(token);
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
        articleManagementServer.removeTheAriticle(author,title,technical);
        return "删除成功";
    }
}

