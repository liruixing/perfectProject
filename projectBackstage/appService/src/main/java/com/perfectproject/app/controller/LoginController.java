package com.perfectproject.app.controller;

import com.perfectproject.app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * 获取眼手机验证码
     * @param request
     * @return
     */
    @RequestMapping(value="/sendAuthCode",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> sendAuthCode(HttpServletRequest request){
        return loginService.sendAuthCode(request);
    }

    /**
     * 登陆
     * @param request
     * @return
     */
    @RequestMapping(value="/login",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request){
        return loginService.login(request);

    }
}
