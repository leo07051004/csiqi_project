package com.csiqi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by cl_kd-user47823 on 2019/11/11.
 *@CrossOrigin(allowedHeaders = "*",allowCredentials = "true",origins = "http://127.0.0.1:8080", maxAge = 3600)
 * 使用CORS解决跨域请求
 */
@Slf4j
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter{
        @Override
        public void addCorsMappings(CorsRegistry registry){//Access-Control-Allow-Origin
            registry.addMapping("/**")// 允许跨域访问的路径//其中* 表示匹配到下一层；** 表示后面不管有多少层，都能匹配
                    .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                    .maxAge(3600)// 预检间隔时间
                    .allowCredentials(true)// 是否发送cookie
                    .allowedHeaders("*");
            log.debug("CrossOrigin跨域请求配置完成。");
           // .allowedOrigins()// 允许跨域访问的源 不配置此项 即可允许所有源
        }
}
