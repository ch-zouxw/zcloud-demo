package com.zxw.zcloud.demo.core.constant;

/**
 * 返回码
 *
 */
public enum CodeEnum {
    /**
     * 参数
     */
    PARAM_EMPTY("P0000", "参数为空"),
    PARAM_ERROR("P0001", "参数错误"),

    /**
     * 鉴权
     */
    AUTH_TIMESTAMP_FAIL("A0001", "无效的时间戳"),
    AUTH_RESUBMIT("A0002", "禁止重复提交"),
    AUTH_IP_BLACK("A0003", "IP黑名单"),
    AUTH_VALID_SIGN__FAIL("A0004", "验签失败"),
    AUTH_ADD_SIGN__FAIL("A0005", "加签失败"),
    AUTH_ID_ERROR("A0006", "用户二要素鉴权不通过"),
    AUTH_ID_BANKCARD_ERROR("A0007", "用户银行卡鉴权不通过"),

    /**
     * 查询
     */
    QUERY_SUCC("Q0000", "查询成功"),

    /**
     * 业务
     */
    BUS_EXCEPTION("B0001", "业务处理异常，请稍后重试"),
    BUS_ERROR("B0002", "业务处理失败，请稍后重试"),
    BUS_SUCCESS("B0003", "业务处理成功"),
    /**
     * 系统
     */
    SYSTEM_ERROR("S0001", "系统服务异常，请稍后重试"),

    /**
     * 未定义
     */
    DEFAULT_EMPTY("Z9999", "未知信息");


    private String code;
    private String msg;

    CodeEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String CODE(){
        return code;
    }

    public String MSG(){
        return msg;
    }

}
