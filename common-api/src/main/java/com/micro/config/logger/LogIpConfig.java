package com.micro.config.logger;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.micro.util.LG;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.config.logger
 * Author:   hhc
 * Date:     2019/3/12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Slf4j
public class LogIpConfig extends ClassicConverter {

    private static String IP=getIp();

    @Override
    public String convert(ILoggingEvent event) {
        return getIp();
    }

    public static String getIp(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error(LG.N(), e);
        }
        return null;
    }
}
