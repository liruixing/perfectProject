package com.perfectproject.app.mapper;


import com.perfectproject.app.beans.TestBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TestMapper {
    @Select("select * from test where name=#{name}")
    List<TestBean> queryTest(@Param("name") String name);
}
