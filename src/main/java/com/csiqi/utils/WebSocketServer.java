package com.csiqi.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * Created by kd-user47823 on 2019/10/30.
 */
@Slf4j
@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //旧：concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //新：使用map对象，便于根据userId来获取对应的WebSocket
    private static ConcurrentHashMap<String,WebSocketServer> websocketList = new ConcurrentHashMap<>();
    //接收sid
    private String userId="";
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        log.info("连接开始websocketList->"+JSON.toJSONString(websocketList));
        WebSocketServer item=websocketList.get(userId);
        if(item!=null){
            log.error("userId="+userId+"已存在！");
            log.info("有新窗口开始监听:"+userId+",当前在线人数为" + getOnlineCount());
        }else{
            websocketList.put(userId,this);
            addOnlineCount();           //在线数加1
            log.info("有新窗口开始监听:"+userId+",当前在线人数为" + getOnlineCount());
        }
        this.userId=userId;
        try {
            sendMessage(JSON.toJSONString(ResultFactory.buildSuccessResult("连接成功")));
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
        log.info("连接结束websocketList->"+JSON.toJSONString(websocketList));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(websocketList.get(this.userId)!=null){
            websocketList.remove(this.userId);
            //webSocketSet.remove(this);  //从set中删除
            subOnlineCount();           //在线数减1
            log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口"+userId+"的信息:"+message);
        if(StringUtils.isNotBlank(message)){
            JSONArray list=JSONArray.parseArray(message);
            for (int i = 0; i < list.size(); i++) {
                try {
                    //解析发送的报文
                    JSONObject object = list.getJSONObject(i);
                    String toUserId=object.getString("toUserId");
                    String contentText=object.getString("contentText");
                    object.put("fromUserId",this.userId);
                    //传送给对应用户的websocket
                    if(StringUtils.isNotBlank(toUserId)&& StringUtils.isNotBlank(contentText)){
                        WebSocketServer socketx=websocketList.get(toUserId);
                        //需要进行转换，userId
                        if(socketx!=null){
                            socketx.sendMessage(JSON.toJSONString(ResultFactory.buildSuccessResult(object)));
                            //此处可以放置相关业务代码，例如存储到数据库
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("推送消息到窗口"+userId+"，推送内容:"+message);
        WebSocketServer item=websocketList.get(userId);
        try {
            if(item!=null){
                item.sendMessage(message);
            }else{
                log.error("userId不存在！");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
