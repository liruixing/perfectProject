package com.perfectproject.app.mapper;

import com.perfectproject.app.beans.AuthCodeInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SMSMapper {
    //检验此验证码是否已经存在
    @Select("SELECT * FROM auth_code where auth_code=#{code}")
    AuthCodeInfo queryAuthCodeByCode(@Param("code") String code);

    //校验验证码
    @Select("SELECT * FROM auth_code where auth_code=#{code} and phone_num=#{phoneNum}")
    AuthCodeInfo queryAuthCode(@Param("phoneNum") String phoneNum, @Param("code") String code);

    @Insert("insert into auth_code(auth_code,phone_num) values(#{auth_code},#{phone_num}})")
    void addAuthCode(@Param("auth_code") String auth_code, @Param("phone_num") String phone_num);


    @Insert("insert into auth_code_his(auth_code,phone_num) values(#{auth_code},#{phone_num})")
    void addAuthCodeHis(@Param("auth_code") String auth_code, @Param("phone_num") String phone_num);

    @Delete("delete from auth_code where id=#{id}")
    void deleteAuthCode(@Param("id") String id);

    //删除过期的验证码,删除五分钟前的所有验证码
    @Delete("delete from auth_code where send_time<DATE_SUB(SYSDATE(),INTERVAL 5 MINUTE)")
    void deleteOverSMS();
}
