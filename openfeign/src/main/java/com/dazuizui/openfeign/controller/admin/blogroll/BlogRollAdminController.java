package com.dazuizui.openfeign.controller.admin.blogroll;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.dazuizui.api.pojo.Blogroll;
import com.dazuizui.openfeign.service.user.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;


@CrossOrigin
@RestController
public class BlogRollAdminController {
    @Autowired
    private UserService userService;

    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private static String accessKeyId = "LTAI4GKpheopX7mLajzfD7jj";
    private static String accessKeySecret = "OYtqw1dszbeANVgTTdODcceZBn4lyO";

    @RequestMapping("/user/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request) throws Exception {
        if (request != null){
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

            //files对应前端的key
            MultipartFile multipartFile = multipartRequest.getFile("files");
            multipartFile.getBytes();//得到文件的二进制流

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            /**
             *  查询文件是否存在
             **/
            // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            boolean found = false;
            found = ossClient.doesObjectExist("dazuiblog", "public/blogroll/"+multipartFile.getOriginalFilename());
            System.out.println(found);
            if (found){
                throw new Exception("介绍图片已经存在");
            }else {
                //将图片上传到oss
                PutObjectRequest putObjectRequest = new PutObjectRequest("dazuiblog","public/blogroll/"+multipartFile.getOriginalFilename(), new ByteArrayInputStream(multipartFile.getBytes()));

                // 上传字符串。
                ossClient.putObject(putObjectRequest);
            }

            // 关闭OSSClient。
            ossClient.shutdown();
        }

        return null;
    }

    /**
     * add a blogroll
     * @param blogroll
     * @param token
     * @return
     */
    @RequestMapping("/user/admin/addABlogroll")
    @ResponseBody
    public String addBlogroll(@RequestBody Blogroll blogroll, @RequestParam("token") String token){
        return userService.addBlogroll(blogroll,token);
    }

    /**
     * get Blogroll list
     * @return
     */
    @GetMapping("/user/admin/getAllBlogroll")
    public String getBlogroll(){
        return userService.getBlogroll();
    }


    @PostMapping("/user/admin/deleteABlogroll")
    public String deleteBlogroll(@RequestParam("token") String token,@RequestParam("name")String name){
        System.out.println(token);
        return userService.deleteBlogroll(token,name);
    }

    /**
     * update the blogroll
     * @param token
     * @param blogroll
     * @return
     */
    @PostMapping("/user/admin/updateTheBlogroll")
    public String updateTheBlogroll(@RequestParam("token") String token,@RequestBody Blogroll blogroll){
        return userService.updateTheBlogroll(token,blogroll);
    }

    @PostMapping("/user/admin/updatetheblogrollinzuiblog")
    public String updateTheBlogRollInZuiblog(@RequestBody Blogroll boll,@RequestParam("token")String token){
        System.out.println(boll);
        return userService.updateTheBlogRollInZuiblog(boll,token);
    }

    /**
     * 查询指定友情链接
     */
    @GetMapping("/user/admin/getTheBlogroll")
    public String queryTheSpecifiedBlogroll(@RequestParam("name") String name){
        return  userService.queryTheSpecifiedBlogroll(name);
    }

    @PostMapping("user/admin/sychronzetionDate")
    public String synchronizationDate(@RequestParam("token") String token){
        return userService.synchronizationDate(token);
    }
}
