package com.dazuizui.users.service.admin.delete;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Service
public interface DeleteUserSerivce {
    /**
     * 删除一个用户
     * @param token
     * @param username
     * @return
     */
    public String deleteTheUser(String token, String username);
}
