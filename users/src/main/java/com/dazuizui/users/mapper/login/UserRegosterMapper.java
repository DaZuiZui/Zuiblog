package com.dazuizui.users.mapper.login;

import com.dazuizui.api.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author  Yida Yang
 * @Time   2021/6/22
 * @Text   用户注册业务操作数据库
 */
@Mapper
public interface UserRegosterMapper {
    /**
     * 通过username查询账户是否存在
     * @return  User.calkss
     */
    @Select("select *from user where username = #{username}")
    public User selectUserbyUsername(User user);

    /**
     * 将用户注册的账号密码添加到mysql
     * @return  User.calkss
     */
    @Insert("insert into userlogin value(null,#{username},#{password})")
    public void addUserlogin(User user);

    /**
     * 将用户信息添加到mysql
     * @return  User.calkss
     */
    @Insert({"insert into userinformation value(null,#{username},#{email},#{year},#{month},#{sex},'https://dazuiblog.oss-cn-hangzhou.aliyuncs.com/test1.jpg',#{name},'user')"})
    public void addUserInformation(User user);

    /**
     * 将用户基础表添加到mysql
     * @return  User.calkss
     */
    @Insert("insert into user value(null,#{username})")
    public void adduser(User user);
}
