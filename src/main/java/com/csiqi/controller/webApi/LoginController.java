package com.csiqi.controller.webApi;

import com.csiqi.model.webVo.VueLoginInfoVo;
import com.csiqi.service.webService.UserService;
import com.csiqi.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import  com.csiqi.utils.ResultFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
@Slf4j
@RestController
public class LoginController {
    /**
     * 登录控制器，前后端分离用的不同协议和端口，所以需要加入@CrossOrigin支持跨域。
     * 给VueLoginInfoVo对象加入@Valid注解，并在参数中加入BindingResult来获取错误信息。
     * 在逻辑处理中我们判断BindingResult知否含有错误信息，如果有错误信息，则直接返回错误信息。
     */
    @Autowired
    private  UserService userService;
    @CrossOrigin
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Result login(@Valid @RequestBody VueLoginInfoVo loginInfoVo, BindingResult bindingResult ,ServletRequest servletRequest) {
        HttpServletRequest req = ((HttpServletRequest)servletRequest);
        HttpSession session=req.getSession();
        if (bindingResult.hasErrors()) {
            String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }
        if (!userService.loginSuccess(loginInfoVo.getUsername(),loginInfoVo.getPassword())) {
            String message = String.format("登陆失败，详细信息[用户名、密码信息不正确]。");
            return ResultFactory.buildFailResult(message);
        }
        session.setAttribute("loginId",loginInfoVo.getUsername());
        log.debug("/api/login:loginId="+session.getAttribute("loginId"));
        return ResultFactory.buildSuccessResult("登陆成功。");
    }
}
