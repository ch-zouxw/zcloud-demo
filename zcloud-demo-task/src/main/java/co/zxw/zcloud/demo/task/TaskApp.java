package co.zxw.zcloud.demo.task;

import com.zxw.zcloud.db.config.DataSourceConfigurer;
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
@Import({DataSourceConfigurer.class,cn.hutool.extra.spring.SpringUtil.class})
@SpringBootApplication
@ServletComponentScan
public class TaskApp {

	public static void main(String[] args) {
		SpringApplication.run(TaskApp.class, args);
	}
}
