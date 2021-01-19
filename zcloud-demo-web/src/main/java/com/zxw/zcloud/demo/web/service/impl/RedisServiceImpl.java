package com.zxw.zcloud.demo.web.service.impl;

import com.zxw.zcloud.common.cache.annotation.RedisCache;
import com.zxw.zcloud.common.cache.annotation.RedisLock;
import com.zxw.zcloud.demo.core.domain.ParamEntity;
import com.zxw.zcloud.demo.web.service.RedisService;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

    /**
     * 注解式缓存
     * @param entity
     * @return
     */
    @RedisCache(expire=30)
    @Override
    public String testCache(ParamEntity entity){
        return "Hello Cache：" + entity.toString();
    }

    /**
     * 注解式分布式锁
     * @param entity
     * @return
     */
    @RedisLock(block = false, expire = 0L)
    @Override
    public String testLock(ParamEntity entity) {
        return "Hello Lock：" + entity.toString();
    }
}
