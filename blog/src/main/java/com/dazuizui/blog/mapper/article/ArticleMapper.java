package com.dazuizui.blog.mapper.article;

import com.dazuizui.api.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author 杨易达
 * 博文数据库层
 */
@Mapper
public interface ArticleMapper {



    /**
     * 分页查询用户发布的文章
     * todo 优化
     * @param author
     * @return
     */
    @Select("SELECT title,author,describes,technical,name from article where author = #{author} and privacy = 'public' limit #{start},#{end}")
    public ArrayList<Article> pagingToGetArticleOfUser(@Param("author")String author,@Param("start")long start,@Param("end")long end);


    /**
     * 获取用户发布的所有文章
     * @param author
     * @param start
     * @param end
     * @return
     */
    @Select("SELECT id,title,author,describes,technical,name from article where author = #{author} and privacy = 'public' order by id desc")
    public ArrayList<Article> pagingToGetArticleOfMyself(@Param("author")String author,@Param("start")long start,@Param("end")long end);


    /**
     * 通过分页去用户发布的博文，此位置只展示标题和描述 作者 展示出来的信息 淘汰
     * @param start
     * @param end
     * @return
     */
    @Select("SELECT title,author,describes,technical,name from article where privacy = 'public' or privacy = 'Public' ORDER BY id desc limit #{start},#{end}")
    public ArrayList<Article> pagingToGetArticleOfUserInNewVer(@Param("start")long start,@Param("end")long end);


    /**
     * 分页查询用户发布的公开博文，
     * sql优化思路：每次查询用上一次分页的最大值
     */
    public ArrayList<Article> pagingQueryBposts(@Param("start")long start );

    /**
     * 模糊查询博文
     */
    @Select("SELECT * FROM article WHERE title Like '%#{articleName}%'")
    public ArrayList<Article> getArticleByFuzzyQuery(@Param("articleName")String articleName);

    public Article queryArticleById(@Param("id")Integer id);

    /**
     * 通过作者名查询发布的所有文件夹
     * @param author
     * @return
     */
    @Select("SELECT id,blogfileclass from article WHERE author = #{author}  GROUP BY blogfileclass ")
    public ArrayList<Article> getAllFolders(@Param("author")String author);

    /**
     * 通过作者名查询所有发布的文章
     * @param map
     * @return
     */
    @Select("select *from article where author = #{author}")
    public ArrayList<Article> getAllArticlebyauthor(HashMap<String,String> map);

    /**
     * 查询用户发布的公开博文
     * Query a user's public blog posts
     */
    @Select("select *from article where author = #{author} and privacy = 'Public'")
    public ArrayList<Article> queryAUsersPublicArticle(@Param("author") String author);

    /**
     * 获取全部文章
     * @return
     */
    @Select("select author,title,technical from article")
    public ArrayList<Article> getAllArticle();

    /**
     * 查询指定分类的文章
     * @param author
     * @param blogfileclass
     * @return
     */
    @Select("select *from article where  author = #{author} and blogfileclass = #{blogfileclass}")
    public ArrayList<Article> getBlogfileclassDataInRedis(@Param("author")String author,@Param("blogfileclass") String blogfileclass);

    /**
     * 通过author 和title查询指定数据
     * @param author
     * @param title
     * @return
     */
    @Select("select *from article where author = #{author} and title = #{title}")
    public Article queryArticleByUsernameAndTitle(@Param("author")String author, @Param("title")String title);

    /**
     * 通过author 和title查询指定数据
     * @param author
     * @param title
     * @return
     */
    public Article queryArticleByUsernameAndTitleAndTechnical(@Param("author")String author, @Param("title")String title,@Param("technical")String technical);

    /**
     * 模糊查询
     * @param title
     * @return
     */
    public List<Article> queryFuzzyQuery(@Param("title") String title);
}
