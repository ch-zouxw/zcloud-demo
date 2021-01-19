package com.zxw.zcloud.demo.dal.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * IP 黑名单
 *
 * @author xykj
 * @date 2020-05-20
 */
@Data
public class IpBlack
{
    private static final long serialVersionUID = 1L;
    /** 主键 */
    private Long id;
    /** IP地址 */
    private String ipAddr;
    /** 备注 */
    private String remark;
    /** 删除标志 */
    private String delFlag;

    /** 创建者 */
    private String createBy;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 数据权限 */
    private String dataScope;

    /**
     * 当前页
     */
    private String pageNum;

    /**
     * 每页显示多少条
     */
    private String pageSize;


}
