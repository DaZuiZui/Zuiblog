package com.dazuizui.users.service.userinfo;

import com.dazuizui.api.pojo.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public interface UserInfoService {
    /**
     * 通过userrname查询name
     * @param name
     * @return
     */

    /**
     * update image data in db
     */
    @PostMapping("/user/admin/updateImageDataIndb")
    public String updateImageDataInDb(@RequestParam("username")String username);

    /**
     * to get user`s name to db by username
     * @param name
     * @return
     */
    public String getNameByUsername(@RequestParam("name") String name);

    //查询用户个人信息
    public String selectUserinfo(String username);
    //修改用户信息
    public String updateUserInfo(User user);
    /**
     * 获取所有用户信息
     */
    public String getUserInfoList(@RequestParam("token") String token);


}
