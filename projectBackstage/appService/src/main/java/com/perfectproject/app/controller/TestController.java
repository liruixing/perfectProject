package com.perfectproject.app.controller;


import com.perfectproject.app.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value="/test",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> test(HttpServletRequest request){

        System.out.println("====================TestController:"+this);
        return testService.test(request);

    }

}
