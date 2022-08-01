package com.dazuizui.users.service.login;

import com.dazuizui.api.pojo.User;
import org.springframework.stereotype.Service;

/**
 * @author 杨易达
 * @Text   用户注册账户信息
 */
@Service
public interface UserRegisterService {
    /**
     * 注册用户
     * @param user
     * @return RequsetJson.class
     */
    public String addUserinformation(User user);

    /**
     * 获取非幂性的token about 用户注入
     */
    public String getNonPower();
}
