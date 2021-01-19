package com.zxw.zcloud.demo.web.controller;

import com.zxw.zcloud.common.cache.annotation.RedisCache;
import com.zxw.zcloud.common.cache.annotation.RedisRateLimit;
import com.zxw.zcloud.common.cache.constant.RateLimitType;
import com.zxw.zcloud.common.cache.utils.RedisUtil;
import com.zxw.zcloud.demo.core.annotation.SignIgnore;
import com.zxw.zcloud.demo.core.domain.ParamEntity;
import com.zxw.zcloud.demo.core.domain.Result;
import com.zxw.zcloud.demo.web.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Api(tags = "Redis")
@RequestMapping("redis")
@RestController
public class TestRedisController {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedisService redisService;

    /**
     * redis限流
     * @param paramEntity
     * @return
     */
    @SignIgnore
    @RedisRateLimit(period = 60, count = 2, limitType = RateLimitType.IP)
    @ApiOperation("redis限流")
    @PostMapping("/limit")
    public Result limit(ParamEntity paramEntity){
        log.info("入参：{}", paramEntity);
        return Result.succeedWith(paramEntity,"");
    }

    @SignIgnore
    @ApiOperation("redis缓存")
    @PostMapping("/cache")
    public Result cache(ParamEntity paramEntity){
        log.info("入参：{}", paramEntity);
        return Result.succeedWith(redisService.testCache(paramEntity),"");
    }

    @SignIgnore
    @ApiOperation("redis锁")
    @PostMapping("/lock")
    public Result lock(ParamEntity paramEntity){
        log.info("入参：{}", paramEntity);
        redisUtil.set("name","zouxw");
        log.info("缓存：{}", redisUtil.get("name"));
        int count = 10;
        final CountDownLatch latch = new CountDownLatch(count);

            for (int i=0;i<count;i++) {
                new Thread() {
                    @Override
                    public void run() {
                            System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("{}", redisService.testLock(paramEntity));
                            System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
                    }
                    ;
                }.start();

                latch.countDown();
            }
        return Result.succeedWith(redisService.testLock(paramEntity),"");
    }
}
