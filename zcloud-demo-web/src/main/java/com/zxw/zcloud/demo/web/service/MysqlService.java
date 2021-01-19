package com.zxw.zcloud.demo.web.service;

import com.zxw.zcloud.demo.core.domain.ParamEntity;

public interface MysqlService {

    String testCache(ParamEntity entity);

    String testLock(ParamEntity entity);
}
