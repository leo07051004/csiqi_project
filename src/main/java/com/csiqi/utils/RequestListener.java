package com.csiqi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by cl_kd-user47823 on 2019/11/8.
 */
@WebListener
@Component
@Slf4j
public class RequestListener implements ServletRequestListener{
    public void requestInitialized(ServletRequestEvent sre){
        //将所有的request请求携带上httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();
        log.debug("-------------WebListener");
    }
    public  RequestListener(){}
    public void requestDestroyed(ServletRequestEvent arg0){
    }
}
