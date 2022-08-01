package com.dazuizui.users.mapper.admin.delete;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DeleteUserMapper {
    /**
     * 删除用户的所有信息
     * @param username 要删除的用户名
     */
    @Delete("delete from banned where username = #{username}")
    public void deleteDataInBannedTableOfMysql(@Param("username")String username);
    @Delete("delete from userlogin where username = #{username}")
    public void deleteDataInUserloginTableOfMysql(@Param("username")String username);
    @Delete("delete from user where username = #{username}")
    public void deleteDataInUserTableOfMysql(@Param("username")String username);
    @Delete("delete from userinformation where username = #{username}")
    public void deleteDataInUserinformationTableOfMysql(@Param("username")String username);

}
