package com.zxw.zcloud.demo.web.controller;

import com.zxw.zcloud.demo.core.annotation.SignIgnore;
import com.zxw.zcloud.demo.core.domain.ParamEntity;
import com.zxw.zcloud.demo.core.domain.Result;
import com.zxw.zcloud.dingtalk.entity.DingtalkLinkRequest;
import com.zxw.zcloud.dingtalk.entity.DingtalkMarkdownRequest;
import com.zxw.zcloud.dingtalk.entity.DingtalkResponse;
import com.zxw.zcloud.dingtalk.service.DingtalkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "钉钉")
@RequestMapping("dingtalk")
@RestController
public class TestDingtalkController {

    @Autowired
    DingtalkService dingtalkService;

    /**
     * 测试钉钉推送
     * @param paramEntity
     * @return
     * 支持的消息格式
     * DingtalkRequest(Text)
     * DingtalkMarkdownRequest(Markdown)
     * DingtalkLinkRequest(Link)
     */
    @SignIgnore
    @ApiOperation("测试钉钉推送")
    @PostMapping("/send")
    public Result testtestDingtalk(ParamEntity paramEntity){
        log.info("入参：{}", paramEntity);
        DingtalkMarkdownRequest markdownRequest = new  DingtalkMarkdownRequest();
        markdownRequest.setKeyword("服务器通知");
        markdownRequest.setTitle("服务异常");
        markdownRequest.setContent("测试失败");
        return Result.succeedWith(dingtalkService.send(markdownRequest),"");
    }
}
