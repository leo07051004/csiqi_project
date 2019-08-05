package com.csiqi.controller;

import com.csiqi.model.UserDomain;
import com.csiqi.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/userView")
    public String index() {
        return "userView";
    }
    @ResponseBody
    @RequestMapping("/add")
    public int addUser(UserDomain user){
        return userService.addUser(user);
    }

    @ResponseBody
    @GetMapping("/all")
    public Object findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize){
        ModelAndView mv =new ModelAndView();
        PageInfo<UserDomain> Vos=userService.findAllUser(pageNum,pageSize);
        mv.addObject("users",Vos);
        return Vos;
    }
    @ResponseBody
    @GetMapping("/selectUserById")
    public Object selectUserById(int userId){
        ModelAndView mv =new ModelAndView();
        UserDomain Vos=userService.selectUserById(userId);
        mv.addObject("userView",Vos);
        return Vos;
    }
    @ResponseBody
    @GetMapping("/removeUserById")
    public Object removeUserById(int userId){
        ModelAndView mv =new ModelAndView();
        Object Vos=userService.removeUserById(userId);
        return Vos;
    }
}


