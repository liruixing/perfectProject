package com.perfectproject.app.service.impl;

import com.perfectproject.app.beans.TestBean;
import com.perfectproject.app.mapper.TestMapper;
import com.perfectproject.app.service.RedisService;
import com.perfectproject.app.service.TestService;
import com.perfectproject.app.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService{

    @Autowired
    TestMapper testMapper;

    @Autowired
    RedisService redisService;
    @Override
    public Map<String, Object> test(HttpServletRequest request) {
        String name=request.getParameter("name");
        List<TestBean> list = testMapper.queryTest(name);

        redisService.set("test","name");
        String redisTest=redisService.get("test");
        System.out.println("redisTest:"+redisTest);

        return ResultUtils.success(list);
    }

}
