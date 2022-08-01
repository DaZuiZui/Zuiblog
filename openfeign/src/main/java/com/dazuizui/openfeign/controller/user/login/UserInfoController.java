package com.dazuizui.openfeign.controller.user.login;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.User;
import com.dazuizui.openfeign.service.user.login.UserService;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;

@CrossOrigin
@RestController
public class UserInfoController {
    @Autowired
    private UserService userService;
    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private static String accessKeyId = "LTAI4GKpheopX7mLajzfD7jj";
    private static String accessKeySecret = "OYtqw1dszbeANVgTTdODcceZBn4lyO";

    @RequestMapping("/user/heanImage/uploadFile")
    @ResponseBody
    public String uploadFile(@RequestParam("username") String username,HttpServletRequest request) throws Exception {
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
            //是否发现
            found = ossClient.doesObjectExist("dazuiblog", "users/"+username+"/headImage/headImage.jpg");
            System.out.println(found);

            //将图片上传到oss
            PutObjectRequest putObjectRequest = new PutObjectRequest("dazuiblog","users/"+username+"/headImage/headImage.jpg", new ByteArrayInputStream(multipartFile.getBytes()));

            // 上传字符串。
            ossClient.putObject(putObjectRequest);

            // 关闭OSSClient。
            ossClient.shutdown();

            if (found){
                return "true";
            }else{
                return "false";
            }
        }
        return "true";
    }

    /**
     * update image data in db
     */
    @PostMapping("/user/admin/updateimagedataindb")
    public String updateImageDataInDb(@RequestParam("username")String username){
        System.out.println(username);
        return userService.updateImageDataInDb(username);
    }



    /**
     * 修改个人信息
     */
    @PostMapping("/user/pr/updateUserInfo")
    public String updateUserInfo(@RequestBody User user){
        System.out.println(user);
        return userService.updateUserInfo(user);
    }

    /**
     * 通过userrname查询name
     * @param username
     * @return
     */
    @GetMapping("/user/getNameByUsername")
    public String getNameByUsername(@RequestParam("username") String username){
        System.out.println(username);
        return userService.getNameByUsername(username);
    }

    /**
     * 用户token解析获取用户信息
     * @param token
     * @return String.json
     */
    @PostMapping("/user/p/checkinsin")
    public String checkinusersin(@RequestParam("token") String token){
        System.out.println(token);
        return userService.checkinusersin(token);
    }

    /**
     * 修改自己的信息
     * @param idemtoken 防止幂等性操作的token
     * @param token     用户的token
     * @param user      修改为的用户信息
     * @return
     */
    @PostMapping("/user/pr/updateInfo")
    public String userUpdateInfo(@RequestParam("idemtoken")String idemtoken,@RequestParam("token")String token,@RequestBody User user){
        return userService.userUpdateInfo(idemtoken,token,user);
    }
}
