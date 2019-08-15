package com.csiqi.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kd-user47823 on 2019/8/14.
 */
@Slf4j
public class CookieUtil {
    public static Boolean setCookie(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie=new Cookie("JSSESIONID",request.getSession().getId());
        cookie.setPath("/csiqi");
        cookie.setValue(request.getSession().getId());
        response.addCookie(cookie);
        return true;
    }
    public static Boolean getCookie(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if(cookies!=null && cookies.length>0){
            for(Cookie cookie:cookies){
                log.debug("name:"+cookie.getName()+"-----value:"+cookie.getValue());
                if(cookie.getName()!=null&&"csiqiCookie".equals(cookie.getName())&& !"".equals(cookie.getValue())&&cookie.getValue()!=null){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

}
