package com.zxw.zcloud.demo.core.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回数据实体类
 *
 * @author zouxw
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    /** 请求标识 true: 请求成功 false：请求失败 */
    private boolean succ;

    /** 返回状态码 业务处理码 */
    private String returnCode;

    /** 返回数据 */
    private T returnData;

    /** 返回信息 */
    private String returnInfo;

    /** 签名 */
    private String sign;

    public static <T> Result<T> succeedWith(T data,String sign){
        return succeedWith( "0000", "succ", data,sign);
    }

    public static <T> Result<T> succeedWith(String returnInfo,T data,String sign){
        return succeedWith( "0000", returnInfo, data,sign);
    }

    private static <T> Result<T> succeedWith(String returnCode,String returnInfo,T data,String sign){
        Result result = new Result();
        result.setSucc(true);
        result.setReturnCode(returnCode);
        result.setReturnInfo(returnInfo);
        result.setReturnData(data);
        result.setSign(sign);
        return result;
    }

    public static <T> Result<T> failedWith(String returnCode,String returnInfo){
        Result result = new Result();
        result.setSucc(false);
        result.setReturnCode(returnCode);
        result.setReturnInfo(returnInfo);
        return result;
    }
}