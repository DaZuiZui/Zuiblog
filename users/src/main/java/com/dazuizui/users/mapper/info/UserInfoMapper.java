package com.dazuizui.users.mapper.info;

import com.dazuizui.api.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface UserInfoMapper {


    @Select("select *from userinformation where username = #{username}")
    public User selectUserInformation(HashMap<String,String> map);

    @Update("update userinformation set profilephotourl = #{profilephotourl} where username = #{username}")
    public void updateTheUserProfilephotourl(User user);

    @Update("update userinformation set name = #{name}, year = #{year},month=#{month},sex=#{sex} where username = #{username}")
    public void updateTheUserInfo(User user);

    /**
     * 获取全部用户信息
     * @return
     */
    @Select("select id,username,email from userinformation")
    public ArrayList<User> selectUserInforList();

    /*
     * 修改信息后修改文章的全部昵称
     */
    @Update("update article set name = #{name} where author = #{username}")
    public void updateNameOfArticle(String username,String name);
}
