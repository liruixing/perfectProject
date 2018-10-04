package com.perfectproject.app.filter;

import com.perfectproject.app.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Think on 2017/8/27.
 */
@Component("secureCheckFilter")
public class SecureCheckFilter extends OncePerRequestFilter {
    private MappingJackson2JsonView jackson2JsonView;
    @Autowired
    RedisService redisService;

    public SecureCheckFilter(){
        super();
        jackson2JsonView = new MappingJackson2JsonView();
    }
    private static final Logger log = LoggerFactory.getLogger(SecureCheckFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token=httpServletRequest.getParameter("token");
        String userId=httpServletRequest.getParameter("userId");
        String path=httpServletRequest.getServletPath();
        if("/login".equals(path)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else if(checkToken(token, userId)){//用户已登录
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else{
            try {
                Map data = new HashMap<String,String>();
                data.put("code", 9999);
                data.put("msg","未登录");
                //返回错误提示
                jackson2JsonView.render(data, httpServletRequest, httpServletResponse);
            }catch (Exception ce){
                log.error(ce.getMessage());
            }
        }
    }



    public boolean checkToken(String token,String userId){
        String reqtoken=redisService.get("loginTokenUserId"+userId);
        System.out.println("请求用户:userId:"+userId+"    reqtoken:"+reqtoken);
        if(reqtoken==null){//token有问题,重新登录
            return false;
//            return getErrorMap(ErrorCodeUtil.ERROR_CODE_TOKEN,"您的账户验证出错，需要重新登录!");
        }
        if(!reqtoken.equals(token)){//token有问题,重新登录
            return false;
//            return getErrorMap(ErrorCodeUtil.ERROR_CODE_TOKEN,"你的账号已再其他地方登录！");
        }
        return true;
    }


}