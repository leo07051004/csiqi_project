package com.csiqi.utils;



import com.csiqi.model.webVo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by kd-user47823 on 2019/8/13.
 */
@Slf4j
@WebFilter(filterName = "myFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("LoginFilter初始化开始...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = ((HttpServletRequest)servletRequest);
        HttpServletResponse response=((HttpServletResponse)servletResponse);
        HttpSession session = req.getSession();
        Object csiqiLoginName=session.getAttribute("csiqiLoginName");
        //String redisSessionId=stringRedisTemplate.opsForValue().get("csiqiLogin:csiqiLoginName"+csiqiLoginName);
        //String redisSessionId=RedisUtils.getString("csiqiLogin","csiqiLoginName"+csiqiLoginName);
        String uri = req.getRequestURI();
        String method=req.getMethod();
        log.debug("web_sessionId:"+session.getId());
        //log.debug("csiqiLoginName_"+csiqiLoginName+"_reids_sessionId:"+redisSessionId);
        if("OPTIONS".equals(method)||uri.endsWith(".jpg") || uri.endsWith(".gif") || uri.endsWith(".png")|| uri.indexOf("/js/")>=0 || uri.indexOf("/css/")>=0|| uri.indexOf("/api/login")>=0) { //不过滤的页面
            filterChain.doFilter(servletRequest, servletResponse);
        }else{//如果session中id和redis中存放的id相同 则通过
            /*if(redisSessionId!=null && redisSessionId.equals(session.getId())){//csiqiLoginName!=null&& !"".equals(csiqiLoginName)
                log.debug("success:过滤器检测是否已登录csiqiLoginName:"+csiqiLoginName);
                //重置redis 中session 过期时间
                stringRedisTemplate.opsForValue().set("csiqiLogin:csiqiLoginName"+csiqiLoginName,session.getId(),1800, TimeUnit.SECONDS);
                //RedisUtils.setStringCountdown("csiqiLogin","csiqiLoginName"+csiqiLoginName,session.getId(),1800);
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                log.debug("error:未登录或登录状态已过期！");//req.getContextPath()+
                response.sendRedirect("http://127.0.0.1:8080"); //重定向到错误页面
            }*/
            UserVo uv=(UserVo)session.getAttribute("userVo");//在Spring Boot中 集成 Spring Session 统一把session存入redis
            if(csiqiLoginName!=null && csiqiLoginName.equals(uv.getUserName())){
                log.debug(csiqiLoginName+"已登录.");
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                log.debug(csiqiLoginName+"未登录或登录状态已过期！");//req.getContextPath()+
                response.sendRedirect("http://127.0.0.1:8080"); //重定向到错误页面
            }
        }
    }

    @Override
    public void destroy() {

    }
}
