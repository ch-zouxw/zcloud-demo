package com.zxw.zcloud.demo.web.service.impl;

import com.zxw.zcloud.db.annotation.DataSource;
import com.zxw.zcloud.db.enums.DataSourceType;
import com.zxw.zcloud.demo.core.domain.ParamEntity;
import com.zxw.zcloud.demo.web.service.MysqlService;
import org.springframework.stereotype.Service;

@Service
public class MysqlServiceImpl implements MysqlService {


    @DataSource(DataSourceType.MASTER)
    @Override
    public String testCache(ParamEntity entity) {
        return null;
    }

    @DataSource(DataSourceType.SLAVE)
    @Override
    public String testLock(ParamEntity entity) {
        return null;
    }
}
