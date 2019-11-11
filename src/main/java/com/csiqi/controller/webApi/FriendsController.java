package com.csiqi.controller.webApi;

import com.csiqi.model.webVo.FriendsVo;
import com.csiqi.model.webVo.UserVo;
import com.csiqi.utils.ResultFactory;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
//@CrossOrigin(allowedHeaders = "*",allowCredentials = "true",origins = "http://127.0.0.1:8080", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/friends")
public class FriendsController {

    @Autowired
    private com.csiqi.service.webService.FriendsService fService;
    @ResponseBody
    @RequestMapping(value = "/friendsList" )
    public Object friendsList(@Valid @RequestBody FriendsVo pf){
        //@RequestParam(name = "pageNum", required = false, defaultValue = "1")//
        //@RequestParam(name = "pageSize", required = false, defaultValue = "10")//
        PageInfo<UserVo> Vos=fService.friendsList(pf);
        return Vos;
    }

    @ResponseBody
    @RequestMapping("/insertFriends")
    public Object acAdd(@Valid @RequestBody FriendsVo av, BindingResult bindingResult){
        int Vos=fService.insertFriends(av);
        if (bindingResult.hasErrors()) {
            String message = String.format("活动新增失败。", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }
        if (Vos<=0) {
            String message = String.format("活动新增失败。");
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult("活动新增成功。");
    }
}
