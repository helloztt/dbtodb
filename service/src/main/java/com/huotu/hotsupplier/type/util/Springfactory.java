package com.huotu.hotsupplier.type.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by admin on 2016/1/25.
 */
public class Springfactory implements ApplicationContextAware {
    private static ApplicationContext applicationContext;//Spring应用上下文环境

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 根据beanName名字取得bean
     *
     * @param name
     * @return
     */
    public static <T> T getBean(Class<T> name) {
        return (T)applicationContext.getBean(name);
    }
}
