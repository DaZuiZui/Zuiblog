package com.dazuizui.users.service.login;

import com.dazuizui.api.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserLoginService {
    public String userlogin(User user);
}
