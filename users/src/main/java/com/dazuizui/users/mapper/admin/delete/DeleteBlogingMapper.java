package com.dazuizui.users.mapper.admin.delete;

import com.dazuizui.api.pojo.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface DeleteBlogingMapper {

    /**
     * 删除用户发布的文章
     * @param author
     * @param author
     */
    @Delete("delete  from article where author = #{author}")
    public void deleteUserUpAritcle(@Param("author")String author);

    /**
     * 查看用户发布的文件夹
     * @param author
     * @return
     */
    @Select("SELECT distinct  privacy,technical,blogfileclass fROM article where author = #{author}")
    public ArrayList<Article> selectBlogfileclassByAuthor(@Param("author")String author);

}
