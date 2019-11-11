package com.csiqi.controller.webApi;

import com.csiqi.utils.Result;
import com.csiqi.utils.ResultFactory;
import com.csiqi.utils.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

//@CrossOrigin(allowedHeaders = "*",allowCredentials = "true",origins = "http://127.0.0.1:8080", maxAge = 3600)
@Controller
@RequestMapping("/api/checkcenter")
public class CheckCenterController {

    //页面请求
    @GetMapping("/socket/{userId}")
    public ModelAndView socket(@PathVariable String userId) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("userId", userId);
        return mav;
    }
    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{userId}")
    public Result pushToWeb(@PathVariable String userId, String message) {
        try {
            WebSocketServer.sendInfo(message,userId);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult(userId+"#"+e.getMessage());
        }
        return ResultFactory.buildSuccessResult(userId);
    }
}



