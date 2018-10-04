package com.perfectproject.app.mapper;

import com.perfectproject.app.beans.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("select * from user where phone=#{phone}")
    UserBean queryUserByPhone(@Param("phone") String phone);

    @Insert("insert into user(phone,nick_name,sex,age,num_gold,num_rmb,num_Integral,regist_time,last_login_time) values" +
            "(#{bean.phone},#{bean.nick_name},#{bean.sex},#{bean.age},#{bean.num_gold},#{bean.num_rmb},#{bean.num_Integral},#{bean.regist_time},#{bean.last_login_time})")
    void insert(@Param("bean")UserBean user);


}
