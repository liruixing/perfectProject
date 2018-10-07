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
    private static final Logger log = LoggerFactory.getLogger(SecureCheckFilter.class);
    @Autowired
    RedisService redisService;

    public SecureCheckFilter(){
        super();
        jackson2JsonView = new MappingJackson2JsonView();
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token=httpServletRequest.getParameter("token");
        String path=httpServletRequest.getServletPath();
        if("/noToken".equals(path)){//表示不需要传token
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else{
            String json=redisService.get("token_"+token);
            if(json==null) {//token不存在,重新登录
                try {
                    Map data = new HashMap<String,String>();
                    data.put("code", 9999);
                    data.put("msg","未登录");
                    //返回错误提示
                    jackson2JsonView.render(data, httpServletRequest, httpServletResponse);
                }catch (Exception ce){
                    log.error(ce.getMessage());
                }
                return;
            }

            //token验证成功
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }




}