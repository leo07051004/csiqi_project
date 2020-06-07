package com.csiqi.service.wxService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.csiqi.utils.MessageUtil;

@Service
public class WechatService {
	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	/**
	 * 处理微信发来的请求 未完成
	 * 
	 * @param request
	 * @return
	 */
	public String weixinPost(HttpServletRequest request) {
		String respMessage = null;
		try {
			Map<String, String> requestMap = MessageUtil.xmlToMap(request);// xml请求解析
			String fromUserName = requestMap.get("FromUserName");// 发送方帐号（open_id）
			String toUserName = requestMap.get("ToUserName");// 公众帐号
			String msgType = requestMap.get("MsgType");// 消息类型
			String content = requestMap.get("Content");// 消息内容

			LOG.debug("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType);
			
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
				// 这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的
				if (content.equals("xxx")) {}
				// 自动回复
				respMessage = getReturnWsMessage(fromUserName,toUserName,MessageUtil.RESP_MESSAGE_TYPE_TEXT,"你好！(自动回复,功能完善中，敬请期待！)");
			} else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				String recvMessage = requestMap.get("Recognition");
				LOG.debug("收到语音消息:recvMessage="+recvMessage);

				String message = null;
				try {
					message = parseWXMessage("serverId",recvMessage,fromUserName);
				} catch(Exception e) {
					LOG.error("解析命令字异常：",e);
				}
				if(message == null) {
					recvMessage = "您发送的语音无法识别机顶盒操作指令，语音为："+recvMessage;
				} else {

				}
				respMessage = getReturnWsMessage(fromUserName,toUserName,MessageUtil.RESP_MESSAGE_TYPE_TEXT,recvMessage);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = requestMap.get("Event");// 事件类型
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅
					respMessage = getReturnWsMessage(fromUserName,toUserName,MessageUtil.RESP_MESSAGE_TYPE_TEXT,"感谢您的关注！有问题请留言哦！");
					String eventKey = requestMap.get("EventKey");//带参数二维码参数
					if(eventKey.indexOf("serverID:") != -1){
						respMessage = parseWXQRCode(fromUserName,toUserName,eventKey);
					}
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
					String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					LOG.error("event未知自定义菜单KEY值,eventKey="+eventKey);
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {// 扫码推送事件
					respMessage = getReturnWsMessage(fromUserName,toUserName,MessageUtil.RESP_MESSAGE_TYPE_TEXT,"感谢扫码关注！有问题请留言哦！");
					String eventKey = requestMap.get("EventKey");//带参数二维码参数
					if(eventKey.indexOf("serverID:") != -1){
						respMessage = parseWXQRCode(fromUserName,toUserName,eventKey);
					}
				} else {
					LOG.error("event未知事件类型,eventType="+eventType);
				}
			} else {
				LOG.error("msgType未知消息类型,msgType="+msgType);
			}
		} catch (Exception e) {
			LOG.error("error......", e);
		}
		return respMessage;
	}

	private String parseWXQRCode(String fromUserName, String toUserName, String eventKey){
		eventKey = eventKey.substring(eventKey.indexOf("serverID:")+9,eventKey.length());
		String sid="";//用户id
		sid=eventKey;
		String urlDomain = "";
		long time = System.currentTimeMillis()/1000;
		String resultUrl = urlDomain+"gd_service/activity/20200120gd_qrcode/mobile/index.jsp?serverId="+sid+"&times="+time;
		String picUrl =  urlDomain+"gd_service/activity/20200120gd_qrcode/mobile/imgs/dot.gif";
		String nameStr = "TV豆换好礼！";
		String descripStr =  "您正在兑换，请填写详细的收货地址和手机号码";
		String respMessage="";
		List<Map<String,Object>> messageList=new ArrayList<Map<String,Object>>();
		Map<String,Object> msgMap=new HashMap<String,Object>();

		msgMap.put("resultUrl", resultUrl);
		msgMap.put("picUrl", picUrl);
		msgMap.put("nameStr", nameStr);
		msgMap.put("descripStr", descripStr);
		messageList.add(msgMap);
		respMessage = getReturnArticlesMessage(fromUserName,toUserName,messageList);
		
		return respMessage;
	}
	
	private String parseWXMessage(String serverId, String msg, String fromUserName) {
		String returnMsg = null;

		if(returnMsg==null ) {
		}
		return returnMsg;
	}
	
	private String getReturnWsMessage(String fromUserName,String toUserName,String msgType,String recvMessage) {
		long createTime = System.currentTimeMillis()/1000;
		String respMessage = "<xml>";
		respMessage += "<ToUserName><![CDATA["+fromUserName+"]]></ToUserName>";
		respMessage += "<FromUserName><![CDATA["+toUserName+"]]></FromUserName>";
		respMessage += "<CreateTime>"+createTime+"</CreateTime>";
		respMessage += "<MsgType><![CDATA["+MessageUtil.RESP_MESSAGE_TYPE_TEXT+"]]></MsgType>";
		respMessage += "<Content><![CDATA["+recvMessage+"]]></Content>";
		respMessage += "</xml>";
		return respMessage;
	}


	private String getReturnArticlesMessage(String fromUserName,String toUserName, List<Map<String,Object>> mesList) {
		String respMessage = "<xml>";
		respMessage += "<ToUserName><![CDATA["+fromUserName+"]]></ToUserName>";
		respMessage += "<FromUserName><![CDATA["+toUserName+"]]></FromUserName>";
		respMessage += "<CreateTime>"+System.currentTimeMillis()/1000+"</CreateTime>";
		respMessage += "<MsgType><![CDATA[news]]></MsgType>";
		
		respMessage += "<ArticleCount>"+mesList.size()+"</ArticleCount>";
		respMessage += "<Articles>";
		for(int i=0;i<mesList.size();i++) {
			respMessage += "<item>";
			respMessage += "<Title><![CDATA["+mesList.get(i).get("nameStr")+"]]></Title>";
			respMessage += "<Description><![CDATA["+mesList.get(i).get("descripStr")+"]]></Description>";
			respMessage += "<PicUrl><![CDATA["+mesList.get(i).get("picUrl")+"]]></PicUrl>";
			respMessage += "<Url><![CDATA["+mesList.get(i).get("resultUrl")+"]]></Url>";
			respMessage += "</item>";
		}
		respMessage += "</Articles>";
		respMessage += "</xml>";
		return respMessage;
	}
}