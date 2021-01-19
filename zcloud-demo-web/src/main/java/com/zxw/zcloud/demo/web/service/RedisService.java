package com.zxw.zcloud.demo.web.service;

import com.zxw.zcloud.demo.core.domain.ParamEntity;

public interface RedisService {

    String testCache(ParamEntity entity);

    String testLock(ParamEntity entity);
}
