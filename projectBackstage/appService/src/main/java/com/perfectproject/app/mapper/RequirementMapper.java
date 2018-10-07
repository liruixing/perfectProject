package com.perfectproject.app.mapper;

import com.perfectproject.app.beans.RequirementBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RequirementMapper {

    @Insert("insert into requirements (type,game_type,unit_price,count,sex,appointment_time,info,status,create_time,update_time) values" +
            "(#{bean.type},#{bean.game_type},#{bean.unit_price},#{bean.count},#{bean.sex},#{bean.appointment_time},#{bean.info},#{bean.status},now(),now())")
    int insertOne(@Param("bean") RequirementBean bean);

    @Select("select count(1) from requirements where user_id=#{userId} and type=#{type}")
    int countByUserId(@Param("userId") String userId,@Param("type") String type);


    @Select("select * from requirements where user_id=#{userId} and type=#{type} limit #{star},#{end}")
    List<RequirementBean> queryRequirementList(@Param("userId") String userId, @Param("start") String star, @Param("end") String end, @Param("type") String type);

    @Select("select * from requirements where user_id=#{userId} limit #{star},#{end}")
    List<RequirementBean> queryRequirementList(@Param("userId") String userId,@Param("star") String start,@Param("end") String end);
}
