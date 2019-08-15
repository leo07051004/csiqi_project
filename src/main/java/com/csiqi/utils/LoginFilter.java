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
        HttpServletResponse response=((HttpServletResponse)servletResponse);
        HttpSession session = req.getSession();
        Object csiqiLoginFlag=session.getAttribute("csiqiLoginFlag");
        String uri = req.getRequestURI();

       String origin = req.getHeader("Origin");
        if(origin == null) {
            origin = req.getHeader("Referer");
        }
        response.setHeader("Access-Control-Allow-Origin", origin);//这里不能写*，*代表接受所有域名访问，如写*则下面一行代码无效。谨记
        response.setHeader("Access-Control-Allow-Credentials", "true");//true代表允许携带cookie
        response.setHeader("Access-Control-Allow-Headers", "SecretKey,AppKey,UniqueKey");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");



        if(uri.endsWith(".jpg") || uri.endsWith(".gif") || uri.endsWith(".png")|| uri.indexOf("/js/")>=0 || uri.indexOf("/css/")>=0|| uri.indexOf("/api/login")>=0) { //不过滤的页面
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
            //resp.sendRedirect(req.getContextPath()+"/commons/warn.jsp"); //重定向到错误页面
            if(csiqiLoginFlag!=null&& !"".equals(csiqiLoginFlag)){
                //CookieUtil.setCookie(req,response);
                log.debug("success--csiqiLoginFlag:"+csiqiLoginFlag);

            }else{
                log.debug("error---");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
