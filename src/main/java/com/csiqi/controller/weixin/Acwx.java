package com.csiqi.controller.weixin;

import com.csiqi.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RestController
@RequestMapping(value = "/api/wechat")
public class Acwx {
    @Value("${DNBX_TOKEN}")
    private String DNBX_TOKEN;
    @Autowired
    private com.csiqi.service.wxService.WechatService wechatService;
    @ResponseBody
    @RequestMapping(value = "/wxConnect" )
    public void wxConnect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8"); // 微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        response.setCharacterEncoding("UTF-8"); // 在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        PrintWriter out = response.getWriter();
        try {
            if (request.getMethod().toLowerCase().equals("get")) {
                String signature = request.getParameter("signature");// 微信加密签名
                String timestamp = request.getParameter("timestamp");// 时间戳
                String nonce = request.getParameter("nonce");// 随机数
                String echostr = request.getParameter("echostr");// 随机字符串

                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
                if (SignUtil.checkSignature(DNBX_TOKEN, signature, timestamp, nonce)) {
                    log.info("Connect the weixin server is successful.");
                    response.getWriter().write(echostr);
                } else {
                    log.error("Failed to verify the signature!");
                }
            } else {
                try {
                    String respMessage = wechatService.weixinPost(request);
                    if(respMessage != null)
                        out.write(respMessage);
                    else
                        out.write("");
                    log.debug("The request completed successfully");
                    log.debug("to weixin server " + respMessage);
                } catch (Exception e) {
                    log.error("Failed to convert the message from weixin!", e);
                }
            }
        } catch (Exception e) {
            log.error("Connect the weixin server is error.", e);
        } finally {
            out.close();
        }
    }
}
