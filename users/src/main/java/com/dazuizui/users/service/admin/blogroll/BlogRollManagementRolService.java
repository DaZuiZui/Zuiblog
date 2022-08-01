package com.dazuizui.users.service.admin.blogroll;

import com.dazuizui.api.pojo.Blogroll;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Service
public interface BlogRollManagementRolService {
    public String addBlogroll(Blogroll blogroll,String token);

    public String getBlogrolllist();

    /**
     * 删除友情链接
     * @param token
     * @param id
     * @return
     */
    public String deleteBlogroll(String token,String id);

    /**
     * 查询指定友情链接
     */
    public String queryTheSpecifiedBlogroll(@RequestParam("id") String id);

    /**
     * update the blogroll
     * @param token
     * @param blogroll
     * @return
     */
    @PostMapping("/user/admin/updateTheBlogroll")
    public String updateTheBlogroll(String token,Blogroll blogroll);


    /**
     * synchornzetion Date
     * @param token
     * @return
     */
    public String synchronizationDate(String token);

    /**
     * 模糊查询友情链接
     */
    public ArrayList<Blogroll> fuzzyQueryBlogroll(String title);
}