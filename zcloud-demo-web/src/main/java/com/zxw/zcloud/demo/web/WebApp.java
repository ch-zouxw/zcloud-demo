package com.zxw.zcloud.demo.web;

import com.zxw.zcloud.db.config.DataSourceConfigurer;
import com.zxw.zcloud.log.annotation.EnableLogging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动
 * @author zouxw
 */
@EnableScheduling
//@EnableRetry
@EnableAsync
//@EnableTransactionManagement
@EnableLogging
@Import({DataSourceConfigurer.class,cn.hutool.extra.spring.SpringUtil.class})
@SpringBootApplication
@ServletComponentScan
public class WebApp {

	public static void main(String[] args) {
		SpringApplication.run(WebApp.class, args);
	}
}
