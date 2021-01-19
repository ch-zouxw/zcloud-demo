package com.zxw.zcloud.demo.web.controller;

import com.zxw.zcloud.demo.core.annotation.SignIgnore;
import com.zxw.zcloud.demo.core.domain.ParamEntity;
import com.zxw.zcloud.demo.core.domain.Result;
import com.zxw.zcloud.mail.jmail.service.MailTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "邮件")
@RequestMapping("mail")
@RestController
public class TestMailController {

    @Autowired
    MailTemplate mailTemplate;

    @SignIgnore
    @ApiOperation("测试邮件推送")
    @PostMapping("/test")
    public Result test(ParamEntity paramEntity){
        log.info("入参：{}", paramEntity);
        mailTemplate.sendSimpleMail("","","");
        return Result.succeedWith(null,"");
    }
}
