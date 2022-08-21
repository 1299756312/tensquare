package com.tensquare.base.controller;

import com.tensquare.common.entity.Result;
import com.tensquare.common.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public Result error(Exception e){
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
