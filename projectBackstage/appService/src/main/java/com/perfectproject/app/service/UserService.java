package com.perfectproject.app.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {
    /**
     * 查询用户信息
     */
    Map<String,Object> queryUserInfo(HttpServletRequest request);

    /**
     * 更新用户信息
     */
    Map<String,Object> updateUserInfo(HttpServletRequest request);
}
