package com.zxw.zcloud.demo.framework.service.impl;

import com.zxw.zcloud.demo.dal.system.dao.IpBlackDao;
import com.zxw.zcloud.demo.dal.system.domain.IpBlack;
import com.zxw.zcloud.demo.framework.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpServiceImpl implements IpService {

    @Autowired
    IpBlackDao ipBlackDao;

    @Override
    public IpBlack selectByIpAddr(String ipAddr){
        return ipBlackDao.selectByIpAddr(ipAddr);
    }

    /**
     * 查询IP黑名单
     *
     * @param id IP黑名单ID
     * @return IP黑名单
     */
    @Override
    public IpBlack selectBizIpBlackById(Long id)
    {
        return ipBlackDao.selectBizIpBlackById(id);
    }

    /**
     * 查询IP黑名单列表
     *
     * @param bizIpBlack IP黑名单
     * @return IP黑名单
     */
    @Override
    public List<IpBlack> selectBizIpBlackList(IpBlack bizIpBlack)
    {
        return ipBlackDao.selectBizIpBlackList(bizIpBlack);
    }

}
