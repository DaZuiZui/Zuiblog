package com.dazuizui.blog.mapper.blogging;

import com.dazuizui.api.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 博客创作数据库操作
 */
@Mapper
public interface BlogingMapper {
    @Update("update article set name = #{name} where author = #{username}")
    public void updateNameInBlogArticle(@Param("name")String name,@Param("username")String username);

    /**
     * 通过作者名和分类还有标题查询文章
     * @param article
     * @return
     */
    @Select("select id from article where title = #{title} and author = #{author} and technical = #{technical}")
    public Article queryDatatoRedisByUsernameAndTitleAndTechical(Article article);

    //将数据添加到数据
    @Insert("insert into detailed_article value(null,#{title},#{author},#{mdtext},#{privacy},#{language},#{technical},#{blogfileclass},#{creatingTime},#{updateTime},#{name},#{html})")
    public void addShowDataToMysql(Article article);

    //将数据添加到数据
    @Insert("insert into article value(null,#{title},#{author},#{privacy},#{language},#{technical},#{blogfileclass},#{creatingTime},#{updateTime},#{name},#{profilephotourl},#{describes})")
    public void addDataToMysql(Article article);
    
    /**
     * 删除个人分类文件夹
     * @param author
     * @param blogfileclass
     */
    @Delete("delete from article where author = #{author} and blogfileclass = #{blogfileclass}")
    public void deleteTheFolders(@Param("author")String author, @Param("blogfileclass")String blogfileclass);

    /**
     * 删除指定文文章
     * @param author
     * @param title
     * @return
     */
    @Delete("delete from article where author = #{author} and title = #{title}")
    public void deleteTheArticle(@Param("author")String author,@Param("title") String title);

    /**
     * 修改用户展示信息
     * @param article
     * @param title
     */
    public void updateArticle(@Param("article") Article article,@Param("oldtitle") String title);


    public void updateArticleDecri(@Param("article") Article article,@Param("oldtitle") String title);

    /**
     * 用户修改信息后调用的改接口用来修改博文的author
     * @param username
     * @param name
     * @return
     */
    @Update("update article set name = #{name} where author = #{username}")
    public void updateNameOfArticle(String username,String name);
}
