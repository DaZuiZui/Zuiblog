package com.dazuizui.users.service.admin.blogroll.impl;


import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.Blogroll;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.mapper.admin.blogroll.BlogrollAdminMapper;
import com.dazuizui.users.redis.admin.blogroll.BlogrollRedis;
import com.dazuizui.users.service.admin.blogroll.BlogRollManagementRolService;
import com.dazuizui.users.utilitys.user.UserInfoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Service
public class BlogRollManagementServiceImpl implements BlogRollManagementRolService {
    @Autowired
    private BlogrollAdminMapper blogrollAdminMapper;
    @Autowired
    private BlogrollRedis blogrollRedis;
    @Autowired
    private UserInfoTool userInfoTool;

    /**
     * 添加友情链接
     * @param blogroll
     * @param token
     * @return
     */
    @Override
    public String addBlogroll(Blogroll blogroll, String token) {
        /**
         * 0.解析token
         */
        String username = JwtUtil.analysisJWT(token);
        /**@@
         * 1.判断是否管理员操作
         */
        HashMap map = new HashMap();
        map.put("username",username);
        User user = blogrollAdminMapper.selectUserInformation(map);
        if (!user.getRole().equals("admin")){
            return "0x9000";
        }

        /**
         * 2.添加到数据库
         */
        blogroll.setId(blogroll+"");
        blogroll.setCreationTime(new Date());
        blogroll.setModificationTime(new Date());
        blogroll.setIntroduceImageUrl("https://dazuiblog.oss-cn-hangzhou.aliyuncs.com/public/blogroll/"+blogroll.getIntroduceImageUrl());
        System.err.println(blogroll);
        blogrollAdminMapper.addBlogroll(blogroll);

        /**
         * 3.添加到redis
         */
        blogrollRedis.addBlogroll(blogroll);
        return "0x9999";
    }

    /**
     * 获取全部数据
     * @return
     */
    @Override
    public String getBlogrolllist() {
        /**
         * Querying data useing redis
         */
        ArrayList<Blogroll> blogrolllist = blogrollRedis.getBlogroll();
        System.err.println(blogrolllist.size());
        /**
         * Querying data useing mysql
         * TO DO
         */
        if (blogrolllist.size() == 0){
            blogrolllist = blogrollAdminMapper.getAllBlogroll();
            if (blogrolllist != null){
                //write date to reids
                for (int i = 0; i < blogrolllist.size(); i++) {
                    System.out.println(blogrolllist.get(i).getId());
                    blogrollRedis.addBlogroll(blogrolllist.get(i));
                }
            }
        }

        return JSONArray.toJSONString(blogrolllist);
    }

    /**
     * 删除友情链接
     * @param token
     * @param name
     * @return
     */
    @Override
    public String deleteBlogroll(String token, String name) {
        //非空判断
        if (token != null && name != null){
            /**
             * 判断是否为管理员操作
             */
            boolean adminswitch =  userInfoTool.DetermineWhetherTheAdministratorIsAnAdministratorByToken(token);
            System.out.println(adminswitch);
            //如果是管理员执行
            if (adminswitch){
                //删除mysql中的数据库
                //deleet date in mysql
                blogrollAdminMapper.deleteBlogroll(name);
                //删除redis中的
                blogrollRedis.deleteBlogrollById(name);
                blogrollRedis.deleteBlogroll(name);
                System.out.println("删除ok");
            }
        }
        return "ok";
    }

    /**
     * 查询指定友情链接
     */
    @Override
    public String queryTheSpecifiedBlogroll(String name) {
        System.out.println("开始查询");

        //查询redis
        Blogroll blogroll = blogrollRedis.queryTheSpecifiedBlogroll(name);
        System.err.println(blogroll);
        if (blogroll == null){
            //Querying data to mysql
            //preparations for the query
            HashMap<String,String> map = new HashMap<>();
            map.put("name",name);
            blogroll = blogrollAdminMapper.queryTheSpecifiedBlogroll(map);

            if (blogroll == null){
                return "0x9003";
            }

            //添加到redis
            blogrollRedis.addBlogroll(blogroll);
        }

        return JSONArray.toJSONString(blogroll);
    }

    /**
     * update the blogroll
     * @param token
     * @param blogroll
     * @return
     */
    @Override
    public String updateTheBlogroll(String token, Blogroll blogroll) {
        //非空判断
        if(token != null && blogroll != null){
            //管理员判断
            if (userInfoTool.DetermineWhetherTheAdministratorIsAnAdministratorByToken(token)){
                System.out.println(blogroll.getIntroduceImageUrl());
                /**
                 * 是否更新圖片
                 */
                // reids 查詢
                Blogroll blogroll1 = blogrollRedis.queryTheSpecifiedBlogroll(blogroll.getId());
                if (blogroll1 == null){
                    //Querying data to mysql
                    //preparations for the query
                    HashMap<String,String> map = new HashMap<>();
                    map.put("id",blogroll.getId());
                    blogroll1 = blogrollAdminMapper.queryTheSpecifiedBlogroll(map);

                    if (blogroll == null){
                        return "0x9003";
                    }

                    //數據同步redis
                    //delete dazuiblog:blogroll`s all data in redis
                    blogrollRedis.deleteAllDate();
                    //从数据库获取数据同步到redis
                    ArrayList<Blogroll> blogrolllist = blogrolllist = blogrollAdminMapper.getAllBlogroll();
                    if (blogrolllist != null){
                        //write date to reids
                        for (int i = 0; i < blogrolllist.size(); i++) {
                            System.out.println(blogrolllist.get(i).getId());
                            blogrollRedis.addBlogroll(blogrolllist.get(i));
                        }
                    }

                }

                System.out.println("asd"+blogroll1);
                /*
                //如果图片有更新就修改图片内容
                if (!blogroll1.getIntroduceImageUrl().equals(blogroll.getIntroduceImageUrl())){
                    blogroll.setIntroduceImageUrl("https://dazuiblog.oss-cn-hangzhou.aliyuncs.com/public/blogroll/"+blogroll.getIntroduceImageUrl());
                    System.out.println("更新后的数据"+blogroll.getIntroduceImageUrl());
                }*/

                //设置更新图片
                //blogroll.setIntroduceImageUrl("https://dazuiblog.oss-cn-hangzhou.aliyuncs.com/public/blogroll/"+blogroll.getIntroduceImageUrl());
                blogroll.setIntroduceImageUrl(blogroll.getIntroduceImageUrl());

                System.out.println("emd:"+blogroll.getIntroduceImageUrl());
                //修改数据库
                blogrollAdminMapper.updateTheBlogroll(blogroll);
                //修改redis
                //blogrollRedis
                blogrollRedis.updateTheBlogroll(blogroll);
            }else{
                return "0x9002";
            }
        }
        return "0x9998";
    }

    /**
     * synchornzetion Date
     * @param token
     * @return
     */
    @Override
    public String synchronizationDate(String token) {
        if (token != null){
            if (userInfoTool.DetermineWhetherTheAdministratorIsAnAdministratorByToken(token)) {
                //delete dazuiblog:blogroll`s all data in redis
                blogrollRedis.deleteAllDate();
                //从数据库获取数据同步到redis
                ArrayList<Blogroll> blogrolllist = blogrolllist = blogrollAdminMapper.getAllBlogroll();
                if (blogrolllist != null) {
                    //write date to reids
                    for (int i = 0; i < blogrolllist.size(); i++) {
                        System.out.println(blogrolllist.get(i).getId());
                        blogrollRedis.addBlogroll(blogrolllist.get(i));
                    }
                }
            }
        }
        return "0x9777";
    }

    /*********************************
     *      EDG夺冠了 又要写代码了.....
     *********************************/


    /**
     * 模糊查询友情链接
     * @param title
     * @return
     */
    @Override
    public ArrayList<Blogroll> fuzzyQueryBlogroll(String title) {
        //非空判断
        if (title!=null) {
            //去redis查询
            ArrayList<Blogroll> blogrollslist = blogrollRedis.fuzzyQueryBlogroll(title);
            //如果redis没有查询到就去mysql
            if (blogrollslist==null) {
                //todo
            }
        }
        return null;
    }
}
