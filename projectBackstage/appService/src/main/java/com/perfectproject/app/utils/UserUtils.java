package com.perfectproject.app.utils;

import com.alibaba.fastjson.JSON;
import com.perfectproject.app.beans.UserBean;
import com.perfectproject.app.service.RedisService;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {


    public static UserBean queryUserByToken(HttpServletRequest httpServletRequest,RedisService redisService){
        String token=httpServletRequest.getParameter("token");

        String json=redisService.get("token_"+token);
        if(json!=null) {//token不存在,重新登录
            UserBean userBean= JSON.parseObject(json,UserBean.class);
            return userBean;
        }
        return null;
    }

}
