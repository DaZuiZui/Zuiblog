package com.dazuizui.blog.mapper.article;

import com.dazuizui.api.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * 管理员对用户博文的操作
 */
@Mapper
public interface ArticleManagementMapper {
    /**
     * 管理员获取用户发布的全部文章
     *
     * 此方法out
     */
    //管理员查询一些文章
    @Select("select *from article ")
    public ArrayList<Article>  adminQueryArticle();
}
