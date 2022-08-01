package com.dazuizui.users.service.admin.adminOperitions;

import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.User;
import com.dazuizui.users.mapper.admin.adminOperitions.AdminOperitionsMapper;
import com.dazuizui.users.redis.info.UserInfoRedis;
import com.dazuizui.users.utilitys.user.UserInfoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YiDa Yang
 */
@Service
public interface AdminOperitionsService {


    /**
     * 模糊查询用户信息列表
     * @param token
     * @param username
     * @return
     */
    public String fuzzyQueryUserInfoByUsername(@RequestParam("token")String token,@RequestParam("username")String username);

    /**
     * 设置管理员
     * @param token
     * @param username
     * @return
     */
    public String settingAnAdmin(@RequestParam("token")String token,@RequestParam("username")String username);

    /**
     * 删除一个管理员
     * @param token
     * @param username
     * @return
     */
    @PostMapping("/user/admin/deleteAnAdmin")
    public String deleteAnAdmin(@RequestParam("token")String token,@RequestParam("username")String username);
}
