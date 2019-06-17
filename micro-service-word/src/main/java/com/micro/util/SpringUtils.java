package com.micro.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by xuetao on 2016/11/16.
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = arg0;
        }
    }

    /**
     * 获取applicationContext
     *
     * @return the application context
     */

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name the name
     * @return the bean
     */

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param <T>   the type parameter
     * @param name  the name
     * @param clazz the clazz
     * @return the bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}