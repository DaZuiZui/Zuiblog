package com.dazuizui.users.mapper.login;

import com.dazuizui.api.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Jack Zui
 *
 * @Text   用户登入业务操作数据库
 */
@Mapper
public interface UserLoginMapper {
    /**
     * 通过username查询账户信息
     * @return  User.calkss
     */
    public User selectUserbyUsername(User user);
}
