package com.perfectproject.app.mapper;

import com.perfectproject.app.beans.RequirementBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RequirementMapper {

    @Insert("insert into requirements (type,game_type,unit_price,count,sex,appointment_time,info,status,create_time,update_time) values" +
            "(#{bean.type},#{bean.game_type},#{bean.unit_price},#{bean.count},#{bean.sex},#{bean.appointment_time},#{bean.info},#{bean.status},now(),now())")
    int insertOne(@Param("bean") RequirementBean bean);


}
