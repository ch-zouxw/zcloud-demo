//package com.zxw.zcloud.demo.web.controller;
//
//import com.zxw.zcloud.demo.core.annotation.SignIgnore;
//import com.zxw.zcloud.demo.core.domain.ParamEntity;
//import com.zxw.zcloud.demo.core.domain.Result;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//
//@Slf4j
//@Api(tags = "阿里云短信")
//@RequestMapping("sms")
//@RestController
//public class TestFluxController {
//
//    @SignIgnore
//    @ApiOperation("短信推送")
//    @PostMapping("/test")
//    public Mono<Result> test(ParamEntity paramEntity){
//        log.info("入参：{}", paramEntity);
//        return Result.succeedWith(null,"");
//    }
//}
