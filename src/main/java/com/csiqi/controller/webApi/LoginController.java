package com.csiqi.controller.webApi;

import com.csiqi.model.webVo.UserVo;
import com.csiqi.model.webVo.VueLoginInfoVo;
import com.csiqi.service.webService.UserService;
import com.csiqi.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import  com.csiqi.utils.ResultFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
//@CrossOrigin(allowedHeaders = "*",allowCredentials = "true",origins = "http://127.0.0.1:8080", maxAge = 3600)
@RestController
public class LoginController {
    /**
     * 登录控制器，前后端分离用的不同协议和端口，所以需要加入@CrossOrigin支持跨域。
     * 给VueLoginInfoVo对象加入@Valid注解，并在参数中加入BindingResult来获取错误信息。
     * 在逻辑处理中我们判断BindingResult知否含有错误信息，如果有错误信息，则直接返回错误信息。
     */
    @Autowired
    private  UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Result login(@Valid @RequestBody VueLoginInfoVo loginInfoVo, BindingResult bindingResult ,HttpServletRequest request,HttpServletResponse response) {
        HttpSession session=request.getSession();
        if (bindingResult.hasErrors()) {
            String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }
        List<UserVo>userVos  = (List<UserVo>)userService.loginSuccess(loginInfoVo.getUsername(),loginInfoVo.getPassword());
        if (userVos==null ||userVos.size()<=0 ) {
            String message = String.format("登陆失败，详细信息[用户名、密码信息不正确]。");
            return ResultFactory.buildFailResult(message);
        }
        session.setAttribute("userVo",userVos.get(0));//登陆成功 把当前用户放进session
        session.setAttribute("csiqiLoginName",userVos.get(0).getUserName());//登陆成功 把登录名放进session ,sessionid 放进redis
        stringRedisTemplate.opsForValue().set("csiqiLogin:csiqiLoginName"+userVos.get(0).getUserName(),session.getId(),1800, TimeUnit.SECONDS);
        //RedisUtils.setStringCountdown("csiqiLogin","csiqiLoginName"+userVos.get(0).getUserName(),session.getId(),1800);
        log.debug(userVos.get(0).getUserName()+"登录成功_web_sessionId:"+session.getId());
        return ResultFactory.buildSuccessResult(userVos.get(0));
    }
    @RequestMapping(value = "/api/requestUserVo", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Result requestUserVo(HttpServletRequest request) {
        HttpSession session=request.getSession();//获取session中的用户信息
        UserVo uvo=(UserVo)session.getAttribute("userVo");
        log.debug("uvo:"+uvo.getUserName());
        return ResultFactory.buildSuccessResult(uvo);
    }
}
