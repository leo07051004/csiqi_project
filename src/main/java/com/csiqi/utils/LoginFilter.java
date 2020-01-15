package com.csiqi.utils;



import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by kd-user47823 on 2019/8/13.
 */
@Slf4j
@WebFilter(filterName = "myFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {

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
        String redisSessionId=RedisUtils.getString("csiqiLogin","csiqiLoginName"+csiqiLoginName);
        String uri = req.getRequestURI();
        String method=req.getMethod();
        log.debug("web_sessionId:"+session.getId());
        log.debug("csiqiLoginName_"+csiqiLoginName+"_reids_sessionId:"+redisSessionId);
        if("OPTIONS".equals(method)||uri.endsWith(".jpg") || uri.endsWith(".gif") || uri.endsWith(".png")|| uri.indexOf("/js/")>=0 || uri.indexOf("/css/")>=0|| uri.indexOf("/api/login")>=0) { //不过滤的页面
            filterChain.doFilter(servletRequest, servletResponse);
        }else{//如果session中id和redis中存放的id相同 则通过
            if(redisSessionId!=null && redisSessionId.equals(session.getId())){//csiqiLoginName!=null&& !"".equals(csiqiLoginName)
                log.debug("success:过滤器检测是否已登录csiqiLoginName:"+csiqiLoginName);
                //重置redis 中session 过期时间
                RedisUtils.setStringCountdown("csiqiLogin","csiqiLoginName"+csiqiLoginName,session.getId(),1800);
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                log.debug("error:未登录或登录状态已过期！");//req.getContextPath()+
                response.sendRedirect("http://127.0.0.1:8080"); //重定向到错误页面
            }
        }
    }

    @Override
    public void destroy() {

    }
}
