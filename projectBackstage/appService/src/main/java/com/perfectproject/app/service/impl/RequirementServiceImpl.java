package com.perfectproject.app.service.impl;

import com.perfectproject.app.beans.RequirementBean;
import com.perfectproject.app.beans.UserBean;
import com.perfectproject.app.mapper.RequirementMapper;
import com.perfectproject.app.service.RedisService;
import com.perfectproject.app.service.RequirementService;
import com.perfectproject.app.utils.ErrorCode;
import com.perfectproject.app.utils.ResultUtils;
import com.perfectproject.app.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class RequirementServiceImpl implements RequirementService {

    @Autowired
    RequirementMapper requirementMapper;

    @Autowired
    RedisService redisService;

    @Override
    public Map<String, Object> release(HttpServletRequest request) {
        String type=request.getParameter("type");//类别,1表示服务，2表示任务
        String game_type=request.getParameter("game_type");//游戏
        String sex=request.getParameter("sex");//要求性别
        String unit_price=request.getParameter("unit_price");//单价

        UserBean userBean=UserUtils.queryUserByToken(request,redisService);

        int fbCount=requirementMapper.countByUserId(userBean.getId()+"",type+"");
        if(fbCount>=1){
            return ResultUtils.error(ErrorCode.ERROR_REQUIEMENT_INSERT_MORE,"超出数量");
        }


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
        int inserCount=requirementMapper.insertOne(requirementBean);
        if(inserCount>0){//成功
            return ResultUtils.success("");
        }else{//失败
            return ResultUtils.error(ErrorCode.ERROR_REQUIEMENT_INSERT);
        }
    }

    /**
     *
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> queryRequirementList(HttpServletRequest request) {
        String page=request.getParameter("page");
        String pageSize=request.getParameter("pageSize");
        String type=request.getParameter("type");

        int pageI=Integer.parseInt(page);
        int pageSizeI=Integer.parseInt(pageSize);

        int star=(pageI-1)*pageSizeI;
        int end=pageI*pageSizeI;

        UserBean userBean=UserUtils.queryUserByToken(request,redisService);

        List<RequirementBean> list;
        if(type==null||"".equals(type)||"0".equals(type)){//查询全部
            list=requirementMapper.queryRequirementList(userBean.getId()+"",star+"",end+"");
        }else{//查询单个
            list=requirementMapper.queryRequirementList(userBean.getId()+"",star+"",end+"",type+"");
        }
        return ResultUtils.success(list);
    }
}
