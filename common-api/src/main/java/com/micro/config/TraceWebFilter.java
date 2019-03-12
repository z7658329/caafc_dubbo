package com.micro.config;
import com.micro.constant.TraceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;


//@Order(1)
//@WebFilter(urlPatterns = {"/*"},filterName = "traceWebFilter")
public class TraceWebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String traceId=MDC.get(TraceConstant.TraceId);
        if(traceId==null){
            MDC.put(TraceConstant.TraceId, UUID.randomUUID().toString().replace("-",""));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

}
