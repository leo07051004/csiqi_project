package com.csiqi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    private Result exceptionHandler(HttpServletRequest rq, Exception e){
        Result result=ResultFactory.buidResult(ResultCode.INTERNAL_SERVER_ERROR,rq.getRequestURI()+":"+e.getMessage(),null);
        return result;
    }
}
