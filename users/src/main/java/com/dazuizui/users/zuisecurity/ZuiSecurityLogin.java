package com.dazuizui.users.zuisecurity;

/**
 * Zui Security System
 * 该接口为登入页面提供安全帮助
 */

public interface ZuiSecurityLogin {
    /**
     * Check to see if the user is banned
     */
    public boolean checkToUser(String username);
}
