package com.zxw.zcloud.demo.web.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 *
 * @author zouxw
 */
public class WebAppListener implements SpringApplicationRunListener {

    public WebAppListener(SpringApplication application, String[]  args){
        System.out.println("constructor");
    }


    @Override
    public void starting() {
        System.out.println("starting...");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("environmentPrepared...");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("contextPrepared...");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("contextLoaded...");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("started...");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("running...");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("failed...");
        exception.printStackTrace();
    }
}
