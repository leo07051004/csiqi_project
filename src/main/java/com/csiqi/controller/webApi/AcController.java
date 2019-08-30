package com.csiqi.controller.webApi;

import com.csiqi.model.webVo.AcAdminVo;
import com.csiqi.utils.ResultFactory;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@CrossOrigin(allowedHeaders = "*",allowCredentials = "true",origins = "http://127.0.0.1:8080", maxAge = 3600)
@RestController
@RequestMapping(value = "/api")
public class AcController {

    @Autowired
    private com.csiqi.service.webService.AcService acService;
    @ResponseBody
    @RequestMapping(value = "/acList" )
    public Object acList(int pageNum,int pageSize){
        //@RequestParam(name = "pageNum", required = false, defaultValue = "1")//
        //@RequestParam(name = "pageSize", required = false, defaultValue = "10")//

        ModelAndView mv =new ModelAndView();
        PageInfo<AcAdminVo> Vos=acService.acList(pageNum,pageSize);
        mv.addObject("acList",Vos);
        return Vos;
    }

    @ResponseBody
    @RequestMapping("/acAdd")
    public Object acAdd(@Valid @RequestBody AcAdminVo av, BindingResult bindingResult){
        int Vos=acService.acAdd(av);
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
