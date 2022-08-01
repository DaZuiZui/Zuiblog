package com.dazuizui.users.mapper.admin.ban;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 封禁 数据库操作
 */
@Mapper
public interface BanMapper {
    @Insert("inset into banned value(null,#{username}")
    public void addUserBanned(@Param("username")String username);
}
