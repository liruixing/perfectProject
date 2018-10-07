package com.perfectproject.app.utils;

public class ErrorCode {
    public final static int ERROR_LOGIN_SEND_AUTHCODE_OVER=1;//超出发送验证码限制
    public final static int ERROR_LOGIN_SEND_AUTHCODE_PHONE=2;//手机号错误

    public final static int ERROR_LOGIN_AUTHCODE=3;//验证码错误
    public final static int ERROR_LOGIN=4;//登录失败


    public final static int ERROR_REQUIEMENT_INSERT=5;//发布失败
    public final static int ERROR_REQUIEMENT_INSERT_MORE=6;//发布多了
}
