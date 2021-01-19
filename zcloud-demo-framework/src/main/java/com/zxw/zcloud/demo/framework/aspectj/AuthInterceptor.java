//package com.zxw.zcloud.demo.framework.aspectj;
//
//import cn.hutool.extra.spring.SpringUtil;
//import cn.hutool.json.JSONUtil;
//import com.zxw.zcloud.common.cache.utils.IPUtils;
//import com.zxw.zcloud.common.cache.utils.RedisUtil;
//import com.zxw.zcloud.demo.core.annotation.SignIgnore;
//import com.zxw.zcloud.demo.core.constant.LyfCodes;
//import com.zxw.zcloud.demo.core.utils.RSAUtils;
//import com.zxw.zcloud.demo.core.utils.SignUtil;
//import com.zxw.zcloud.demo.dal.system.domain.IpBlack;
//import com.zxw.zcloud.demo.framework.service.impl.IpServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.lang.Nullable;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * AuthInterceptor
// *
// * @author zouxw
// */
//@Slf4j
//public class AuthInterceptor implements HandlerInterceptor {
//
//    private long start = System.currentTimeMillis();
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // preHandle是在请求执行前执行的
//        start = System.currentTimeMillis();
//        log.info("==============请求路径====================");
//        log.info("URI：{}",request.getRequestURI());
//        log.info("Method:{}",request.getMethod());
//        log.info("==============打印参数====================");
//        Map<String,String> map = new HashMap();
//        Enumeration paramNames = request.getParameterNames();
//        while (paramNames.hasMoreElements()) {
//            String paramName = (String) paramNames.nextElement();
//            map.put(paramName, request.getParameter(paramName));
//            log.info("{}: {}",paramName,request.getParameter(paramName));
//        }
//        if(true){
//            return true;
//        }
//        String[] paths = request.getRequestURI().split("/");
//        if ("/auth/dispense".equals(request.getRequestURI())){
//            return true;
//        }
//
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//
//            // 忽略带SignIgnore注解的请求, 不做后续校验
//            SignIgnore signIgnore = handlerMethod.getMethodAnnotation(SignIgnore.class);
//            if (signIgnore == null) {
//                // 校验时间戳
//                if (!map.containsKey("timestamp") || !validTimestamp(map.get("timestamp"))){
//                    return buildErrorResult(response, LyfCodes.AUTH_TIMESTAMP_FAIL.name());
//                }
//
//                // 校验随机值
//                if (!map.containsKey("nonce") || !validNonce("",map.get("nonce"))){
//                    return buildErrorResult(response, LyfCodes.AUTH_RESUBMIT.name());
//                }
//
//                // 校验IP黑名单
//                if ( !validIpAddr(IPUtils.getIpAddress())){
//                    return buildErrorResult(response, LyfCodes.AUTH_IP_BLACK.name());
//                }
//
//                if (!verifySign(map)) {
//                    return buildErrorResult(response, LyfCodes.AUTH_VALID_SIGN__FAIL.name());
//                }
//            }
//        }
//
//        return true;
//    }
//
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
//        log.info("Interception cost="+(System.currentTimeMillis()-start));
//    }
//
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
//        // 视图渲染完成之后才执行
//    }
//
//    /**
//     * 校验时间戳
//     *
//     * @param timestamp
//     * @return
//     */
//    private boolean validTimestamp(String timestamp){
//        if(StringUtils.isEmpty(timestamp)){
//            log.error("未传时间戳");
//            return false;
//        }
//
//        try {
//            Date clientDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(timestamp);
//            // 时间差值过大
//            if((System.currentTimeMillis() - clientDate.getTime()) > (10 * 60 * 1000L)){
//                log.error("数据可能被篡改,{}-{}",System.currentTimeMillis(),clientDate.getTime());
//                return false;
//            }
//        } catch (ParseException e) {
//            log.error("时间戳转换异常");
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 校验随机值，防重复提交
//     *
//     * @param accessKey
//     * @param nonce
//     * @return
//     */
//    private boolean validNonce(String accessKey,String nonce){
//        if(nonce == null){
//            // 非法请求
//            log.error("随机值为空");
//        }else{
//            //判断请求是否重复攻击
//            RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
//            String key_nonce = accessKey + nonce;
//            if(redisUtil.hasKey(key_nonce) == false){
//                redisUtil.set(key_nonce, nonce,10);
//                return true;
//            }else{
//                log.error("重复提交");
//            }
//        }
//        return false;
//    }
//
//    /**
//     * IP 黑名单校验
//     * @return
//     */
//    private boolean validIpAddr(String ipAddr){
//        String key = "IP_BLACK_LIST";
//        IpServiceImpl ipService = SpringUtil.getBean("ipServiceImpl");
//        if (ipService == null){
//            log.error("未找到企业服务实例");
//            return false;
//        }
//
//        IpBlack ipBlack = ipService.selectByIpAddr(ipAddr);
//        if (ipBlack != null){
//            log.error("命中IP黑名单");
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 校验签名
//     *
//     * @param map
//     * @return
//     */
//    private boolean verifySign(Map<String,String> map) throws Exception {
//
//        if (!map.containsKey("sign")) {
//            return false;
//        }
//
//        String signature = map.get("sign");
//        if (StringUtils.isEmpty(signature)) {
//            return false;
//        }
//        map.remove("sign");
//
//        String agentCd = map.get("agentCd");
//        if (StringUtils.isEmpty(agentCd)) {
//            return false;
//        }
//
//        // 验签
//        return verifySign(map,signature,agentCd);
//    }
//
//    private boolean verifySign(Map<String,String> map,String signature,String agentCd) throws Exception {
//
//        Set<String> keySet = map.keySet();
//        String[] keyArray = keySet.toArray(new String[keySet.size()]);
//        Arrays.sort(keyArray);
//        StringBuilder sortQueryStringTmp = new StringBuilder();
//        for (String k : keyArray) {
//            if (k.equals("sign")) {
//                continue;
//            }
//            // 过滤参数为空的项
//            if (map.get(k).trim().length() > 0) {
//                sortQueryStringTmp.append(SignUtil.specialUrlEncode(k)).append("=").
//                        append(SignUtil.specialUrlEncode(map.get(k).trim())).append("&");
//            }
//        }
//        if (sortQueryStringTmp.length() > 0) {
//            sortQueryStringTmp = sortQueryStringTmp.deleteCharAt(sortQueryStringTmp.length() - 1);
//        }
//
//        SysPlatformKeysMapper bean = SpringUtil.getBean(SysPlatformKeysMapper.class);
//        SysPlatformKeys sysPlatformKeys = bean.selectByCompanyIdAndSignType(agentCd, "1");
//        if (Objects.isNull(sysPlatformKeys)){
//            log.error("当前agentCd未配置密钥,agentCd = {}",agentCd);
//            return false;
//        }
//
//        String lyfPublicKey = sysPlatformKeys.getPublicKeyIsv();
//        if (StringUtils.isEmpty(lyfPublicKey)) {
//            log.error("agentCd公钥为空,agentCd = {}",agentCd);
//            return false;
//        }
//
//        String signStr = sortQueryStringTmp.toString();
//        // 验签
//        return RSAUtils.verifySign(signStr, signature, lyfPublicKey);
//    }
//
////    /**
////     * 返回错误
////     *
////     * @param response
////     * @param errorMsg
////     */
////    private boolean buildErrorResult(HttpServletResponse response,String  errorMsg){
////        log.error("返回错误：{}",errorMsg);
////        response.setCharacterEncoding("UTF-8");
////        response.setContentType("text/javascript; charset=utf-8");
////        try {
////            response.getWriter().write(
////                    JSONUtil.toJsonStr(Result.failedWith("","",errorMsg,errorMsg)));
////        } catch (IOException e) {
////            log.error("验签失败：{}",e.getMessage());
////        }
////        return false;
////    }
//
//    /**
//     * 返回验签失败
//     *
//     * @param response
//     * @param codeName
//     * @return
//     */
//    private boolean buildErrorResult(HttpServletResponse response,String codeName){
//        log.error("返回错误：{}",codeName);
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/javascript; charset=utf-8");
//        try {
//            response.getWriter().write(
//                    JSONUtil.toJsonStr(Result.failedWith("", LyfCodes.valueOf(codeName).CODE(),
//                            LyfCodes.AUTH_VALID_SIGN__FAIL.MSG(),
//                            LyfCodes.valueOf(codeName).MSG())));
//        } catch (IOException e) {
//            log.error("验签返回异常：{}",e.getMessage());
//        }
//        return false;
//    }
//}
