package com.dazuizui.users.redis.admin.blogroll;

import com.dazuizui.api.pojo.Blogroll;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public interface BlogrollRedis {
    //添加友情链接
    public void addBlogroll(Blogroll blogroll);

    //获取全部友情链接
    public ArrayList<Blogroll>getBlogroll();

    /**
     * 删除友情连接
     * @param name
     */
    public void deleteBlogroll(String name);

    /**
     * 模糊查询友情链接
     */
    public ArrayList<Blogroll> fuzzyQueryBlogroll(String title);
    /**
     * 删除指定友情链接
     * @param id
     */
    public void deleteBlogrollById(String id);

    /**
     * 查询指定友情链接
     */
    public Blogroll queryTheSpecifiedBlogroll(String id);

    /**
     * update the blogroll
     * @param blogroll
     * @return
     */
    public void updateTheBlogroll(Blogroll blogroll);

    /**
     * 删除redis中的全部数据
     */
    public void deleteAllDate();
}
