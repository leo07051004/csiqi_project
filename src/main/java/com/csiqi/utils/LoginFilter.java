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
        log.debug("filter初始化开始...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = ((HttpServletRequest)servletRequest);
        HttpServletResponse resp = ((HttpServletResponse)servletResponse);
        HttpSession session = req.getSession();
        String uri = req.getRequestURI();
        if(uri.endsWith(".jpg") || uri.endsWith(".gif") || uri.endsWith(".png")|| uri.indexOf("/js/")>=0 || uri.indexOf("/css/")>=0|| uri.indexOf("/api/login")>=0) { //不过滤的页面
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            Object loginId = session.getAttribute("loginId");
            if(loginId == null ){
                log.debug("filter失败:"+uri);
            }else{
                log.debug("filter成功:"+uri);
            }
            log.debug("loginId="+loginId);
            //resp.sendRedirect(req.getContextPath()+"/commons/warn.jsp"); //重定向到错误页面
        }
    }

    @Override
    public void destroy() {

    }
}
