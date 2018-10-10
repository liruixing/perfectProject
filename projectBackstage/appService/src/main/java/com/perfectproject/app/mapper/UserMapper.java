package com.perfectproject.app.mapper;

import com.perfectproject.app.beans.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where id=#{id}")
    UserBean queryUserById(@Param("id") String id);


    @Update("update user set nick_name=#{nickName},sex=#{sex},age=#{age} where id=#{userId}")
    int updateUserInfo(@Param("userId") String userId,@Param("nickName") String nickName,@Param("sex") String sex,@Param("age") String age);


}
