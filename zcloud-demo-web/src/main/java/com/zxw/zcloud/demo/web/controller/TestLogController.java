package com.zxw.zcloud.demo.web.controller;

import com.zxw.zcloud.demo.core.annotation.SignIgnore;
import com.zxw.zcloud.demo.core.domain.ParamEntity;
import com.zxw.zcloud.demo.core.domain.Result;
import com.zxw.zcloud.demo.web.service.MysqlService;
import com.zxw.zcloud.log.annotation.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j

@Api(tags = "日志服务")
@RequestMapping("log")
@RestController
public class TestLogController {

    @Autowired
    MysqlService mysqlService;

    /**
     * 注解式日志
     * @param paramEntity
     * @return
     */
    @LogAnnotation(module = "Log")
    @SignIgnore
    @ApiOperation("注解式日志")
    @PostMapping("/save")
    public Result save(ParamEntity paramEntity){
        log.info("入参：{}", paramEntity);
        return Result.succeedWith(paramEntity,"");
    }


}
