package com.zxw.zcloud.demo.framework.service;

import com.zxw.zcloud.demo.dal.system.domain.IpBlack;

import java.util.List;

public interface IpService {

    IpBlack selectByIpAddr(String ipAddr);


    /**
     * 查询IP黑名单
     *
     * @param id IP黑名单ID
     * @return IP黑名单
     */
    IpBlack selectBizIpBlackById(Long id);

    /**
     * 查询IP黑名单列表
     *
     * @param bizIpBlack IP黑名单
     * @return IP黑名单集合
     */
    List<IpBlack> selectBizIpBlackList(IpBlack bizIpBlack);

}
