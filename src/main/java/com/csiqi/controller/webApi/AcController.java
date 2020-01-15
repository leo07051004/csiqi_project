package com.csiqi.controller.webApi;

import com.csiqi.model.webVo.AcAdminVo;
import com.csiqi.utils.ResultFactory;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@CrossOrigin(allowedHeaders = "*",allowCredentials = "true",origins = "http://127.0.0.1:8080", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping(value = "/api")
public class AcController {

    @Autowired
    private com.csiqi.service.webService.AcService acService;
    @ResponseBody
    @RequestMapping(value = "/acList" )
    @Cacheable(cacheNames = "AcController")//添加redis缓存 , key = "#pf"  key不设置的话会默认为所有参数
    public Object acList(@RequestBody PageInfo pf){
        //@RequestParam(name = "pageNum", required = false, defaultValue = "1")//
        //@RequestParam(name = "pageSize", required = false, defaultValue = "10")//
        PageInfo<AcAdminVo> Vos=acService.acList(pf.getPageNum(),pf.getPageSize());
        return Vos;
    }

    @ResponseBody
    @RequestMapping("/acAdd")
    @CacheEvict(cacheNames = "AcController", allEntries=true)//allEntries=true 清除cacheNames = "AcController"下的所有缓存
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
