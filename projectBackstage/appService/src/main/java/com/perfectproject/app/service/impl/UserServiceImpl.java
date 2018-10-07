package com.perfectproject.app.service.impl;

import com.perfectproject.app.beans.UserBean;
import com.perfectproject.app.mapper.UserMapper;
import com.perfectproject.app.service.RedisService;
import com.perfectproject.app.service.UserService;
import com.perfectproject.app.utils.ResultUtils;
import com.perfectproject.app.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    RedisService redisService;

    @Autowired
    UserMapper userMapper;
    @Override
    public Map<String, Object> queryUserInfo(HttpServletRequest request) {
        UserBean userBean= UserUtils.queryUserByToken(request,redisService);
        return ResultUtils.success(userMapper.queryUserById(userBean.getId()+""));
    }

    @Override
    public Map<String, Object> updateUserInfo(HttpServletRequest request) {
        return null;
    }


}
