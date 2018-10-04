package com.perfectproject.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.perfectproject.app.beans.AuthCodeInfo;
import com.perfectproject.app.beans.UserBean;
import com.perfectproject.app.mapper.LoginMapper;
import com.perfectproject.app.mapper.SMSMapper;
import com.perfectproject.app.service.LoginService;
import com.perfectproject.app.service.RedisService;
import com.perfectproject.app.utils.MD5Util;
import com.perfectproject.app.utils.ResultUtils;
import com.perfectproject.app.utils.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    SMSMapper smsMapper;

    @Autowired
    RedisService redisService;

    @Override
    public Map<String, Object> sendAuthCode(HttpServletRequest request) {
        String phone=request.getParameter("phone");

        String code= SMSUtil.getAuthCode(smsMapper);//获取到验证码
        int rqCode= SMSUtil.sendSmsCode(phone,code);//发送验证码
        System.out.println("发送验证码返回:"+rqCode);
        if(rqCode==0){//表示已经发送成功
            AuthCodeInfo authCodeInfo=smsMapper.queryAuthCode(phone,code);
            if(authCodeInfo!=null){//已经存在，需要先删除
                smsMapper.deleteAuthCode(authCodeInfo.getId()+"");//删除
            }
            smsMapper.addAuthCode(""+code,phone);//添加一条短信记录
            smsMapper.addAuthCodeHis(""+code,phone);//添加历史记录
            return ResultUtils.success(null);//成功
        }else{//发送失败
            return ResultUtils.error(rqCode);
        }
    }

    @Override
    public Map<String, Object> login(HttpServletRequest request) {
        String phone=request.getParameter("phone");
        String authCode=request.getParameter("authCode");

        AuthCodeInfo authCodeInfo=smsMapper.queryAuthCode(phone,authCode);

        if(authCodeInfo==null){//表示没有，直接返回错误
            return ResultUtils.error(1,"验证码不存在");
        }
        //允许登陆
        UserBean user=loginMapper.queryUserByPhone(phone);
        if(user==null){//需要注册
            user=regist(phone);
        }

        if(user==null){//登陆失败,返回错误
            return ResultUtils.error(2,"登陆失败");
        }

        //登陆成功，生成token
        String token= MD5Util.getMD5(user.getId()+""+Calendar.getInstance().getTimeInMillis());
        //将token存到redis
        redisService.set("token_"+token,JSON.toJSONString(user));

        user.setToken(token);
        return ResultUtils.success(user);//成功
    }

    /**
     * 注册
     */
    private UserBean regist(String phone){
        UserBean user=new UserBean();
        user.setPhone(phone);
        user.setNick_name("");
        user.setNum_gold(0);
        user.setNum_Integral(0);
        user.setNum_rmb(0);
        loginMapper.insert(user);
        return user;
    }

}
