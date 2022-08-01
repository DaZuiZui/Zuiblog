package com.dazuizui.users.controller.admin.blogroll;

import com.dazuizui.api.pojo.Blogroll;
import com.dazuizui.users.service.admin.blogroll.BlogRollManagementRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yida Yang
 * 友情链管理操作控制器
 */
@CrossOrigin
@RestController
public class BlogRolManagementController {
    @Autowired
    private BlogRollManagementRolService blogRollAdminRolService;

    /**
     * 添加友情链接
     * @param blogroll
     * @param token
     * @return
     */
    @RequestMapping("/user/admin/addABlogroll")
    @ResponseBody
    public String addBlogroll(@RequestBody Blogroll blogroll,@RequestParam("token") String token){
        return blogRollAdminRolService.addBlogroll(blogroll,token);
    }


    /**
     * 删除友情链接
     * @param token
     * @param name
     * @return
     */
    @PostMapping("/user/admin/deleteABlogroll")
    public String deleteBlogroll(@RequestParam("token") String token,@RequestParam("name") String name){
        System.out.println(token);
        return blogRollAdminRolService.deleteBlogroll(token,name);
    }

    /**
     * 查询指定友情链接
     */
    @GetMapping("/user/admin/getTheBlogroll")
    public String queryTheSpecifiedBlogroll(@RequestParam("name") String name){
        System.out.println(name);
        return blogRollAdminRolService.queryTheSpecifiedBlogroll(name);
    }

    /**
     * 获取友情列表
     * @return
     */
    @GetMapping("/user/admin/getAllBlogroll")
    public String getBlogroll(){
        System.out.println("查询所有友情链接");

        return blogRollAdminRolService.getBlogrolllist();
    }


    /**
     * update the blogroll
     * @param token
     * @param blogroll
     * @return
     */
    @PostMapping("/user/admin/updateTheBlogroll")
    public String updateTheBlogroll(@RequestParam("token") String token,@RequestBody Blogroll blogroll){
        System.out.println(blogroll.getIntroduceImageUrl());
        return blogRollAdminRolService.updateTheBlogroll(token,blogroll);
    }

    @PostMapping("/user/admin/updatetheblogrollinzuiblog")
    public String updateTheBlogRollInZuiblog(@RequestBody Blogroll boll,@RequestParam("token")String token){
        System.out.println("1231231");
        System.out.println(boll.getIntroduceImageUrl());
        return blogRollAdminRolService.updateTheBlogroll(token,boll);
    }

    /**
     * synchornzetion Date
     * @param token
     * @return
     */
    @PostMapping("user/admin/sychronzetionDate")
    public String synchronizationDate(@RequestParam("token") String token){
        return blogRollAdminRolService.synchronizationDate(token);
    }
}