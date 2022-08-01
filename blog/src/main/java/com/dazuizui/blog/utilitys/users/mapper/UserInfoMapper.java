package com.dazuizui.blog.utilitys.users.mapper;

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
}
