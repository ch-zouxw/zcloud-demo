package com.zxw.zcloud.demo.framework.listener;

import com.zxw.zcloud.demo.core.utils.SpringContextUtil;
import com.zxw.zcloud.demo.framework.event.DingtalkEvent;
import com.zxw.zcloud.dingtalk.entity.DingtalkMarkdownRequest;
import com.zxw.zcloud.dingtalk.entity.DingtalkResponse;
import com.zxw.zcloud.dingtalk.service.DingtalkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * ServiceNotifyListener
 * @author zouxw
 */
@Slf4j
@Component
public class DingtalkListener {

    @EventListener
    public void onApplicationEvent(DingtalkEvent notifyEvent) {
        DingtalkService dingtalkService = SpringContextUtil.getBean(DingtalkService.class);
        log.info(">>>>>>>>>ServiceNotifyListener>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("收到了：" + notifyEvent.getSource() + "消息时间：" + notifyEvent.getTimestamp());
        DingtalkMarkdownRequest markdownRequest = new DingtalkMarkdownRequest();
        markdownRequest.setKeyword("服务器通知");
        markdownRequest.setTitle(notifyEvent.getTitle());
        markdownRequest.setContent(notifyEvent.getMessage());
        DingtalkResponse response = dingtalkService.send(markdownRequest);
        if (response!=null && StringUtils.equals("0000",response.getCode())){
            log.info( "钉钉推送成功");
        }
    }

}
