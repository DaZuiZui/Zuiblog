package com.dazuizui.users.mapper.admin.blogroll;

import com.dazuizui.api.pojo.Blogroll;
import com.dazuizui.api.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface BlogrollAdminMapper {
    /**
     * 查询用户信息
     * @param map
     * @return
     */
    @Select("select *from userinformation where username = #{username}")
    public User selectUserInformation(HashMap<String,String> map);

    @Insert("insert into blogroll value(null,#{name},#{englishName},#{englishIntroduce},#{introduce},#{introduceImageUrl},#{creationTime},#{modificationTime},#{url})")
    public void addBlogroll(Blogroll blogroll);

    @Delete("delete from blogroll where name = #{name}")
    public void deleteBlogroll(String id);

    @Select("select *from blogroll")
    public ArrayList<Blogroll> getAllBlogroll();

    @Select("select *from blogroll where name = #{name}")
    public Blogroll queryTheSpecifiedBlogroll(HashMap<String,String > map);

    @Update("update blogroll " +
            "set " +
                "name = #{name} , " +
                "englishName = #{englishName} ," +
                " englishIntroduce = #{englishIntroduce} ," +
                "introduce = #{introduce} ," +
                "introduceImageUrl = #{introduceImageUrl}," +
                "url = #{url}" +
            "where id = #{id}")
    public void updateTheBlogroll(Blogroll blogroll);
}
