package com.example.controller;

import com.example.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    /**
     *传参示例
     */
    @GetMapping("/homeHost")
    public Result homeHost(String device1 ,String platform){
        log.info("device1:{}" ,device1);
        log.info("platform:{}" ,platform);
        return Result.success();
    }
}
