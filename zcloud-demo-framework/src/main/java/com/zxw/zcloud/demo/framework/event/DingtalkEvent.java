package com.zxw.zcloud.demo.framework.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * ServiceNotifyEvent
 * @author zouxw
 */
@Data
public class DingtalkEvent extends ApplicationEvent {

    private String msgType;

    private String keyword;

    private String title;

    private String message;

    public DingtalkEvent(Object source) {
        this(source,null,null,null,null);
    }

    public DingtalkEvent(Object source, String message) {
        this(source,"dingtalk","服务器通知","服务通知",message);
    }

    public DingtalkEvent(Object source, String msgType, String keyword, String title, String message) {
        super(source);
        this.msgType = msgType;
        this.title = title;
        this.keyword = keyword;
        this.message = message;
    }
}
