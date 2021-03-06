package com.perfectproject.app.controller;

import com.perfectproject.app.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(method = RequestMethod.POST)
public class RequirementController {

    @Autowired
    RequirementService requirementService;

    /**
     * 发布
     */
    @RequestMapping(value="/release")
    @ResponseBody
    public Map<String,Object> release(HttpServletRequest request){
        return requirementService.release(request);
    }

    /**
     * 查询列表
     */
    @RequestMapping(value="/queryRequirementList")
    @ResponseBody
    public Map<String,Object> queryRequirementList(HttpServletRequest request){
        return requirementService.queryRequirementList(request);
    }

}
