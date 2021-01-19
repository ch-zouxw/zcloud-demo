package com.zxw.zcloud.demo.framework.publisher;

import com.zxw.zcloud.demo.framework.event.DingtalkEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * ServiceNotifyPublisher
 * @author zouxw
 */
@Component
public class DingtalkPublisher {

    private final ApplicationContext applicationContext;

    @Autowired
    public DingtalkPublisher(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void publish(Object source,String message) {
        applicationContext.publishEvent(new DingtalkEvent(source,  message));
    }

}
