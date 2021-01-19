//package com.zxw.zcloud.demo.framework.aspectj;
//
//import cn.hutool.extra.spring.SpringUtil;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.xykj.laiyifen.api.core.annotation.SignIgnore;
//import com.xykj.laiyifen.api.core.dao.SysPlatformKeysMapper;
//import com.xykj.laiyifen.api.core.log.GlobalLogService;
//import com.xykj.laiyifen.api.core.model.Result;
//import com.xykj.laiyifen.api.core.model.SysPlatformKeys;
//import com.xykj.laiyifen.api.core.util.LogThreadLocalUtils;
//import com.xykj.laiyifen.api.core.util.RSAUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//import java.util.Arrays;
//import java.util.Set;
//
//@Slf4j
//@ControllerAdvice(basePackages = "com.xykj.laiyifen.api.platform.controller")
//public class EncryptResponseBodyAdvice implements ResponseBodyAdvice {
//
//    @Autowired
//    private GlobalLogService globalLogService;
//
//    @Override
//    public boolean supports(MethodParameter methodParameter, Class aClass) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//        String agentCd = ((ServletServerHttpRequest)serverHttpRequest).getServletRequest().getParameter("agentCd");
//        SignIgnore signIgnore = methodParameter.getMethodAnnotation(SignIgnore.class);
//        //不加密或者不为标准返回对象
//        if(null != signIgnore || !(body instanceof Result)){
//            return body;
//        }
//        Object responseObject = encodeBody(body,agentCd);
//        globalLogService.updateSysLog(responseObject, LogThreadLocalUtils.getKey());
//        return responseObject;
//    }
//
//    private Object encodeBody(Object object,String agentCd) {
//        Result result = (Result)object;
//        JSONObject beanJson = JSONUtil.parseObj(JSONUtil.parseObj(result).toString());
//        Set<String> keySet =beanJson.keySet();
//        String[] keyArray = keySet.toArray(new String[keySet.size()]);
//        Arrays.sort(keyArray);
//        StringBuffer sortQueryStringTmp = new StringBuffer();
//        try {
//            for (String k : keyArray) {
//                // 过滤参数为空的项
//                if(beanJson.get(k) != null){
//                    if (beanJson.get(k).toString().trim().length() > 0) {
//                        sortQueryStringTmp.append(k).append("=").
//                                append(beanJson.get(k).toString().trim()).append("&");
//                    }
//                }
//            }
//            if (sortQueryStringTmp.length() > 0) {
//                sortQueryStringTmp = sortQueryStringTmp.deleteCharAt(sortQueryStringTmp.length() - 1);
//            }
//            SysPlatformKeysMapper bean = SpringUtil.getBean(SysPlatformKeysMapper.class);
//            SysPlatformKeys sysPlatformKeys = bean.selectByCompanyIdAndSignType(agentCd, "1");
//            String sign = RSAUtils.doSign(sortQueryStringTmp.toString(), sysPlatformKeys.getPrivateKeyPlatform());
//            result.setSign(sign);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("加签失败");
//            return result;
//        }
//        return result;
//    }
//}
