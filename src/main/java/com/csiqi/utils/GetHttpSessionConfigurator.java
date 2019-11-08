package com.csiqi.utils;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by cl_kd-user47823 on 2019/11/8.
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig conif, HandshakeRequest request, HandshakeResponse response){
        HttpSession httpSession=(HttpSession)request.getHttpSession();
        if(httpSession !=null){
            conif.getUserProperties().put(HttpSession.class.getName(),httpSession);
        }
    }

}
