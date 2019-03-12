package com.micro.config;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.micro.constant.TraceConstant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.config
 * Author:   hhc
 * Date:     2019/3/8
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
//@Component
//@Aspect
public class TraceApsepct{
    private Logger logger = LoggerFactory.getLogger(getClass());
    private long paramSize = 2000;
    private int viewSize=400;
    private String seg="--------------------------------------";
    @Pointcut("execution(* com.micro.api..*.*(..))")
    public void tracePointcut(){}


    @Before("tracePointcut()")
    public void doBefore(JoinPoint joinPoint){
        //logger.info("doBefore2222222222222222222222222");
    }

    @Around("tracePointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] paramValeArray = joinPoint.getArgs();
        String[] paramKeys = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        StringBuilder paramsKVSB = new StringBuilder();
        String tempValue;
        int size;
        int length = paramKeys.length;
        for (int i = 0; i < length; i++) {
            paramsKVSB.append(paramKeys[i]);
            tempValue = String.valueOf(paramValeArray[i]);
            paramsKVSB.append("=");
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
        String requestPath=joinPoint.getSignature().getName();
        logger.info("========>dubbo_request  requestPath:{},requestParams:{}", requestPath, requestParams);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed(paramValeArray);
        String resultStr = JSON.toJSONString(result);
        long end = System.currentTimeMillis();
        Long cost=end-start;
        MDC.put("cost",cost.toString());
        logger.info("========>dubbo_response:{},cost:{}ms",resultStr,cost);
        return result;
    }
}
