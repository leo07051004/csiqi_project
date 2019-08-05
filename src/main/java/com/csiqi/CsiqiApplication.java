package com.csiqi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@MapperScan("com.csiqi.dao")
public class CsiqiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsiqiApplication.class, args);
    }

}
