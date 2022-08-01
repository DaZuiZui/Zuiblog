package com.dazuizui.openfeign.service.user.login;

import com.dazuizui.api.pojo.Blogroll;
import com.dazuizui.api.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "USER")
public interface UserService {

    /**
     * 获取非幂性的token
     */
    @PostMapping("/user/reg/getNonPowerTokenString")
    public String getNonPower();

    /**
     * 通过userrname查询name
     * @param username
     * @return
     */
    @GetMapping("/user/getNameByUsername")
    public String getNameByUsername(@RequestParam("username") String username);

    /**
     * 设置一个管理员
     * @param token
     * @param username
     * @return
     */
    @PostMapping("/user/admin/settingAnAdmin")
    public String settingAnAdmin(@RequestParam("token")String token, @RequestParam("username")String username);


    /**
     * 删除一个用户
     * @param token
     * @param username
     * @return
     */
    @PostMapping("/user/admin/deleteuser")
    public String deleteUser(@RequestParam("token") String token,@RequestParam("username") String username);
    /**
     * 封禁用户
     */
    @PostMapping("/user/admin/banned")
    public String banUser(@RequestParam("token")String token, @RequestParam("beBanUsername")String beBanUsername);

    /**
     * 通过username获取用户信息
     */
    @PostMapping("/user/admin/getuserInfo")
    public String getUserInfor(@RequestParam("username")  String username);

    /**
     * 获取所有用户信息
     */
    @PostMapping("/user/admin/getuserInfoList")
    public String getUserInfoList(@RequestParam("token") String token);

    /**
     * 用户登入
     * @param user
     * @return String.class
     */
    @PostMapping("/user/p/userlogin")
    public String userlogin(@RequestBody User user);

    /**
     * 修改个人信息
     */
    @PostMapping("/user/pr/updateUserInfo")
    public String updateUserInfo(@RequestBody User user);

    /**
     * 修改用户头像
     * @param username
     * @return
     */
    @PostMapping("/user/admin/updateimagedataindb")
    public String updateImageDataInDb(@RequestParam("username")String username);



    /**
     * 用户注册
     * @param user
     * @return String.class
     */
    @PostMapping("/user/p/userregister")
    public String registerUser(@RequestBody User user,@RequestParam("token")String token);

    /**
     * 用户token解析获取用户信息
     * @param token
     * @return String.json
     */
    @PostMapping("/user/p/checkinsin")
    public String checkinusersin(@RequestParam("token") String token);

    @PostMapping("/user/admin/addABlogroll")
    public String addBlogroll(@RequestBody Blogroll blogroll, @RequestParam("token") String token);

    @GetMapping("/user/admin/getAllBlogroll")
    public String getBlogroll();

    @PostMapping("/user/admin/deleteABlogroll")
    public String deleteBlogroll(@RequestParam("token") String token,@RequestParam("name")String name);

    /**
     * 查询指定友情链接
     */
    @GetMapping("/user/admin/getTheBlogroll")
    public String queryTheSpecifiedBlogroll(@RequestParam("name") String name);

    /**
     * update the blogroll
     * @param token
     * @param blogroll
     * @return
     */
    @PostMapping("/user/admin/updateTheBlogroll")
    public String updateTheBlogroll(@RequestParam("token") String token,@RequestBody Blogroll blogroll);

    @PostMapping("/user/admin/updatetheblogrollinzuiblog")
    public String updateTheBlogRollInZuiblog(@RequestBody Blogroll boll,@RequestParam("token")String token);

    /**
     * synchronzetion date
     * @param token
     * @return
     */
    @PostMapping("user/admin/sychronzetionDate")
    public String synchronizationDate(@RequestParam("token") String token);

    /**
     * 模糊查询用户信息
     */
    @GetMapping("/user/admin/fuzzyQueryUserInfoByUsername")
    public String fuzzyQueryUserInfoByUsername(@RequestParam("token")String token,@RequestParam("username")String username);

    /**
     * 删除一个管理员
     * @param token
     * @param username
     * @return
     */
    @PostMapping("/user/admin/deleteAnAdmin")
    public String deleteAnAdmin(@RequestParam("token")String token,@RequestParam("username")String username);

    /**
     * 修改自己的信息
     * @param idemtoken 防止幂等性操作的token
     * @param token     用户的token
     * @param user      修改为的用户信息
     * @return
     */
    @PostMapping("/user/pr/updateInfo")
    public String userUpdateInfo(@RequestParam("idemtoken")String idemtoken,@RequestParam("token")String token,@RequestBody User user);
    }

