package com.zxw.zcloud.demo.dal.system.dao;

import com.zxw.zcloud.demo.dal.system.domain.IpBlack;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * IP 黑名单
 *
 * @author zouxw
 */
@Component
@Mapper
public interface IpBlackDao {

    /**
     * 根据IP地址查询
     * @param ipAddr
     * @return
     */
    IpBlack selectByIpAddr(@Param("ipAddr") String ipAddr);

    /**
     * 查询IP黑名单
     *
     * @param id IP黑名单ID
     * @return IP黑名单
     */
    public IpBlack selectBizIpBlackById(Long id);

    /**
     * 查询IP黑名单列表
     *
     * @param bizIpBlack IP黑名单
     * @return IP黑名单集合
     */
    public List<IpBlack> selectBizIpBlackList(IpBlack bizIpBlack);

    /**
     * 新增IP黑名单
     *
     * @param bizIpBlack IP黑名单
     * @return 结果
     */
    public int insertBizIpBlack(IpBlack bizIpBlack);

    /**
     * 修改IP黑名单
     *
     * @param bizIpBlack IP黑名单
     * @return 结果
     */
    public int updateBizIpBlack(IpBlack bizIpBlack);

    /**
     * 删除IP黑名单
     *
     * @param id IP黑名单ID
     * @return 结果
     */
    public int deleteBizIpBlackById(Long id);

    /**
     * 批量删除IP黑名单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizIpBlackByIds(Long[] ids);

}
