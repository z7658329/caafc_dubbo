package com.micro.config.logger;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import com.micro.constant.TraceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


import java.util.UUID;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.config
 * Author:   hhc
 * Date:     2019/3/7
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Activate(group = { Constants.PROVIDER, Constants.CONSUMER })
public class TraceDubboFilter implements Filter {

    private  static Logger logger = LoggerFactory.getLogger(TraceDubboFilter.class);
    private long paramSize = 2000;
    private int viewSize=400;
    private String seg="--------------------------------------";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId =RpcContext.getContext().getAttachment(TraceConstant.TraceId);
        if(traceId==null){
            traceId=MDC.get(TraceConstant.TraceId);
            if(traceId==null){
                traceId=UUID.randomUUID().toString().replace("-","");
            }
            RpcContext.getContext().setAttachment(TraceConstant.TraceId,traceId);
            Result result=invoker.invoke(invocation);
            return result;
        }else {
            MDC.put(TraceConstant.TraceId, traceId);
            MDC.put("IP",LogIpConfig.getIp());
            StringBuilder paramsKVSB = new StringBuilder();
            Object[]params=invocation.getArguments();
            for(int i = 0; i < params.length; i++){
                Object param=params[i];
                String paramString=param.toString();
                if (paramString.length() > paramSize) {
                    paramString = paramString.substring(0, viewSize) + seg +paramString.substring(paramString.length() - viewSize);
                }
                paramsKVSB.append(paramString);
                if(i<params.length-1){
                    paramsKVSB.append(", ");
                }
            }
            String requestParams = paramsKVSB.toString();
            String requestPath=invocation.getMethodName();
            logger.info("========>dubbo_request  requestPath:{},requestParams:{}", requestPath, requestParams);
            long start = System.currentTimeMillis();
            Result result=invoker.invoke(invocation);
            String resultStr = JSON.toJSONString(result.getValue());
            long end = System.currentTimeMillis();
            Long cost=end-start;
            MDC.put("cost",cost.toString());
            logger.info("========>dubbo_response:{},cost:{}ms",resultStr,cost);
            return result;
        }

    }

}