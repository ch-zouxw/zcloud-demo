package com.zxw.zcloud.demo.core.domain;

import lombok.Builder;
import lombok.Data;

/**
 * 接参实体基类
 *
 * @author zouxw
 */
@Data
public class ParamEntity {
    /** 机构编号 */
    private String agentCd;
    /** 企业签名key */
    private String companySecretKey;
    /** 请求的时间戳:20180101112345 */
    private String timestamp;
    /** 防重随机数 */
    private String nonce;
    /** 业务参数： */
    private String biz_content;
    /** 签名类型 */
    private String signType;
    /** 签名 */
    private String sign;

}
