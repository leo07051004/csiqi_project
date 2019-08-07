package com.csiqi.controller.webApi;

import com.csiqi.model.webVo.AcAdminVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/api")
public class AcController {

    @Autowired
    private com.csiqi.service.webService.AcService acService;
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/acList")
    public Object acList(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
            int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
            int pageSize){
        ModelAndView mv =new ModelAndView();
        PageInfo<AcAdminVo> Vos=acService.acList(pageNum,pageSize);
        mv.addObject("acList",Vos);
        return Vos;
    }
}
