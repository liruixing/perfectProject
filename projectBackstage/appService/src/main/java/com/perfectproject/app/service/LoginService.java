package com.perfectproject.app.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginService {
    /**
     * 获取验证码
     */
    Map<String,Object> sendAuthCode(HttpServletRequest request);

    /**
     * 执行登陆
     */
    Map<String,Object> login(HttpServletRequest request);

}
