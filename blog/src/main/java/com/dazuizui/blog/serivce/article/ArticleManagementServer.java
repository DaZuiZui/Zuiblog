package com.dazuizui.blog.serivce.article;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 管理员操作博文
 */
@Service
public interface ArticleManagementServer {
    /**
     * 管理员获取用户发布的全部文章
     */
    public String adminGetAllPublicArticle(@RequestParam("token")String token);

    /**
     * 移除指定文章
     * @param author    文章作者
     * @param title     标题
     * @param technical 分类
     * @return
     */
    public String removeTheAriticle(String author,String title,String technical);
}
