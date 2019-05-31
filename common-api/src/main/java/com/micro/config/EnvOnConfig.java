package com.micro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.util
 * Author:   hhc
 * Date:     2019/4/26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Component
public class EnvOnConfig {

    private String envs="prd";

    @Value("${spring.profiles.active:prd}")
    private String active;

    private boolean envOn=true;

    @PostConstruct
    void setEnvOn(){
        if(!envs.contains(active)){
            envOn=false;
        }
    }

    public boolean isEnv(){
        return envOn;
    }
}
