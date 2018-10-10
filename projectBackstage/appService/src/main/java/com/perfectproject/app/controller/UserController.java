package com.perfectproject.app.controller;

import com.perfectproject.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(method = RequestMethod.POST)
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 获取眼手机验证码
     */
    @RequestMapping(value="/queryUserInfo")
    @ResponseBody
    public Map<String,Object> queryUserInfo(HttpServletRequest request){
        return userService.queryUserInfo(request);
    }

    /**
     * 修改用户信息
     */
    @RequestMapping(value="/updateUserInfo")
    @ResponseBody
    public Map<String,Object> updateUserInfo(HttpServletRequest request){
        return userService.updateUserInfo(request);
    }


    /**
     * 修改用户头像
     */
    @RequestMapping(value="/updateUserInfo")
    @ResponseBody
    public Map<String,Object> updateUserPhoto(HttpServletRequest request){
        return userService.updateUserPhoto(request);
    }


}
