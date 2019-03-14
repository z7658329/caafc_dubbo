package com.micro.config.logger;

import com.alibaba.fastjson.JSON;
import com.micro.constant.TraceConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.config
 * Author:   hhc
 * Date:     2018/11/14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Component
@Aspect
@Slf4j
public class LoggerAspect {

    private long paramSize = 2000;
    private int viewSize=400;
    private String seg="--------------------------------------";

    @Pointcut("execution(* com.micro.controller..*.*(..))")
    public void loggerPointcut(){}

    @Around("loggerPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestPath = request.getContextPath() + request.getServletPath();
        Object[] paramValeArray = joinPoint.getArgs();
        String[] paramKeys = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        StringBuilder paramsKVSB = new StringBuilder();
        String tempValue;
        int size;
        int length = paramKeys.length;
        for (int i = 0; i < length; i++) {
            paramsKVSB.append(paramKeys[i]);
            paramsKVSB.append("=");
            tempValue = String.valueOf(paramValeArray[i]);
            size = tempValue.length();
            if (size > paramSize) {
                tempValue = tempValue.substring(0, viewSize) + seg + tempValue.substring(size - viewSize);
            }
            paramsKVSB.append(tempValue);
            if (i < length - 1) {
                paramsKVSB.append(",");
            }
        }
        String requestParams = paramsKVSB.toString();
        String method = request.getMethod();
        String userId = request.getHeader("userId");
        String code = request.getHeader("code");
        String channelId = request.getHeader("channelId");
        String traceId=MDC.get(TraceConstant.TraceId);
        if(traceId==null){
            MDC.put(TraceConstant.TraceId, UUID.randomUUID().toString().replace("-",""));
        }
        MDC.put("userId",userId);
        MDC.put("code",code);
        MDC.put("channelId",channelId);
        MDC.put("requestPath",requestPath);
        MDC.put("method",method);
        MDC.put("IP",LogIpConfig.getIp());
        log.info("========>http_request  requestPath:{},userId:{},code:{},channelId:{},method:{},requestParams:{}", requestPath,userId, code, channelId,   method, requestParams);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        Long cost=end-start;
        MDC.put("cost",cost.toString());
        String resultStr = JSON.toJSONString(result);
        if(resultStr.length()> paramSize){
            resultStr = resultStr.substring(0, viewSize) + seg + resultStr.substring(resultStr.length() - viewSize);
        }
        log.info("========>http_response:{},userId:{},cost:{}ms",resultStr, userId,cost);
        MDC.clear();
        return result;
    }
}
