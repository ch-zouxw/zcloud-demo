package co.zxw.zcloud.demo.task.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * TestJob
 * @author zouxw
 */
@Slf4j
@Component
public class TestJob {

    @XxlJob(value = "syncBindCardStarus")
    public ReturnT<String> syncTest(String param) {

        return ReturnT.SUCCESS;
    }

}
