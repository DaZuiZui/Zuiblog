package com.dazuizui.users.controller.userinfo;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.RequestJson;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.service.userinfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;    //用户信息业务层

    /**
     * update image data in db
     */
    @PostMapping("/user/admin/updateimagedataindb")
    public String updateImageDataInDb(@RequestParam("username")String username){
        System.out.println("123");
        return userInfoService.updateImageDataInDb(username);
    }



    /**
     * 通过userrname查询name
     * @param username
     * @return
     */
    @GetMapping("/user/getNameByUsername")
    public String getNameByUsername(@RequestParam("username") String username){
        System.out.println(username);
        return userInfoService.getNameByUsername(username);
    }

    /**
     * 通过username获取用户信息
     */
    @PostMapping("/user/admin/getuserInfo")
    public String getUserInfor(@RequestParam("username")  String username){
        return userInfoService.selectUserinfo(username);
    }

    /**
     * 获取所有用户信息
     */
    @PostMapping("/user/admin/getuserInfoList")
    public String getUserInfoList(@RequestParam("token") String token){
        return userInfoService.getUserInfoList(token);
    }

    /**
     * 修改个人信息
     */
    @PostMapping("/user/pr/updateUserInfo")
     public String updateUserInfo(@RequestBody User user){
        return userInfoService.updateUserInfo(user);
     }

    /**
     * 用户token解析获取用户信息
     * @param token
     * @return String.json
     */
    @PostMapping("/user/p/checkinsin")
    public String checkinusersin(String token){
        //解析密钥
        String username = null;
        try {
            username = JwtUtil.analysisJWT(token);
        } catch (Exception e) {
            System.out.println("解析失败");
            RequestJson requestJson = new RequestJson("0x0005","还没有进行登入，该没有进行登入不会叫user强制登入",false);
            return JSONArray.toJSONString(requestJson);
        }

        //查询用户信息并且返回
        return userInfoService.selectUserinfo(username);
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
        //修改用户的个人资料
        userInfoService.updateUserInfo(user);


        return "修改成功!";
    }
}
