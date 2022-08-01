package com.dazuizui.users.service.admin.delete.impl;

import com.dazuizui.api.pojo.Article;
import com.dazuizui.users.mapper.admin.delete.DeleteBlogingMapper;
import com.dazuizui.users.mapper.admin.delete.DeleteUserMapper;
import com.dazuizui.users.redis.admin.delete.impl.DeleteUserRedisImpl;
import com.dazuizui.users.service.admin.delete.DeleteUserSerivce;
import com.dazuizui.users.utilitys.user.UserInfoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class DeleteUserSerivceImpl implements DeleteUserSerivce {
    @Autowired
    private UserInfoTool userInfoTool;           //用户工具类
    @Autowired
    private DeleteUserRedisImpl deleteUserRedis;
    @Autowired
    private DeleteBlogingMapper blogingMapper;
    @Autowired
    private DeleteUserMapper deleteDataInTableOfMysql;

    /**
     * 删除一个用户
     * @param token
     * @param username
     * @return
     */
    @Override
    @Transactional
    public String deleteTheUser(String token, String username){
        //非空判断
        if (token != null && username != null){
            //判断是否位管理员操作
            boolean adminAccess = userInfoTool.DetermineWhetherTheAdministratorIsAnAdministratorByToken(token);

            System.out.println(blogingMapper.selectBlogfileclassByAuthor(username));
            //获取用户发布的博文信息
            ArrayList<Article> userUpAllArticle = blogingMapper.selectBlogfileclassByAuthor(username);
            //如果是管理员就执行删除操作
            if (adminAccess) {
                //删除用户的所有数据 in mysql
                deleteDataInTableOfMysql.deleteDataInBannedTableOfMysql(username);
                deleteDataInTableOfMysql.deleteDataInUserloginTableOfMysql(username);
                deleteDataInTableOfMysql.deleteDataInUserTableOfMysql(username);
                deleteDataInTableOfMysql.deleteDataInUserinformationTableOfMysql(username);
                //删除用户信息 in redis
                deleteUserRedis.delteUserAllInfo(username);
                /**
                 * 删除数据库和redis中的用户发布的文章
                 */
                //删除数据库中的用户发布的文章
                blogingMapper.deleteUserUpAritcle(username);
                //删除用户博文信息在reids
                deleteUserRedis.deleteTheArticle(userUpAllArticle,username);
            }
        }
        return "删除成功";
    }
}
