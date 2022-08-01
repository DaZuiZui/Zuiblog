package com.dazuizui.users.mapper.admin.adminOperitions;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminOperitionsMapper {
    @Update("update userinformation set role = 'admin' where username = #{username}")
    public void settingRole(@Param("username")String username);
}
