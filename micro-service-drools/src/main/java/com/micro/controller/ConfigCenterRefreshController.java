package com.micro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/_easyconf")
public class ConfigCenterRefreshController {

    @Autowired
    private AbstractEnvironment environment;


    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "/refresh")
    public String refresh() {
        PropertySource<?> target = null;
        String configLocation = environment.getProperty("spring.config.location");
        if (!StringUtils.isEmpty(configLocation)) {
            for (PropertySource<?> ps : environment.getPropertySources()) {
                if (ps.getName().startsWith("applicationConfig") && StringUtils.startsWithIgnoreCase(configLocation, "http")) {
                    target = ps;
                    break;
                }
            }
        }

        if (target == null) {
            return "--spring.config.location没有配置，或者不是一个url地址，你可能没有使用配置中心，没有配置可以刷新";
        }
        Properties pros = new Properties();
        try {
            PropertiesLoaderUtils.fillProperties(pros, new UrlResource(configLocation));
        } catch (IOException e) {
            e.printStackTrace();
            return "reload failed , " + e.getMessage();
        }

        // 更新到spring容器
        Map map = (Map) target.getSource();
        map.clear();
        map.putAll(pros);

        //更新所有bean @value注解的字段。
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanName);
            for (Field f : bean.getClass().getDeclaredFields()) {
                Value valueAnno = f.getAnnotation(Value.class);
                if (valueAnno == null) {
                    continue;
                }
                String key = valueAnno.value();
                if (key == null) {
                    continue;
                }
                key = key.replace("${", "").replace("}", "");
                key = key.split(":")[0];
                if (map.containsKey(key)) {
                    f.setAccessible(true);
                    try {
                        f.set(bean, map.get(key));
                    } catch (Exception e) {
                        return e.getMessage();
                    }
                }
            }
        }
        return "all @Value field update success";
    }
}