package com.perfectproject.app.service.impl;

import com.perfectproject.app.beans.RequirementBean;
import com.perfectproject.app.mapper.RequirementMapper;
import com.perfectproject.app.service.RequirementService;
import com.perfectproject.app.utils.ErrorCode;
import com.perfectproject.app.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Map;

@Service
public class RequirementServiceImpl implements RequirementService {

    @Autowired
    RequirementMapper requirementMapper;

    @Override
    public Map<String, Object> release(HttpServletRequest request) {
        String type=request.getParameter("type");//类别,1表示服务，2表示任务
        String game_type=request.getParameter("game_type");//游戏
        String sex=request.getParameter("sex");//要求性别
        String unit_price=request.getParameter("unit_price");//单价

        RequirementBean requirementBean=new RequirementBean();
        requirementBean.setType(Integer.parseInt(type+""));
        requirementBean.setGame_type(Integer.parseInt(game_type+""));
        requirementBean.setSex(Integer.parseInt(sex+""));
        requirementBean.setUnit_price(Long.parseLong(unit_price+""));
        if("2".equals(type)){//表示发布任务
            String count=request.getParameter("count");//次数
            String appointment_time=request.getParameter("appointment_time");//预约时间
            String info=request.getParameter("info");//详细说明


            requirementBean.setCount(Integer.parseInt(""+count));
            requirementBean.setInfo(""+info);
            requirementBean.setStatus(1);
            try {
                requirementBean.setAppointment_time(DateFormat.getDateInstance().parse(""+appointment_time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }



        //插入一个发布
        int count=requirementMapper.insertOne(requirementBean);
        if(count>0){//成功
            return ResultUtils.success("");
        }else{//失败
            return ResultUtils.error(ErrorCode.ERROR_REQUIEMENT_INSERT);
        }
    }
}
