package com.shev.itembank.common.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationUtil implements ApplicationContextAware
{

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        if (SpringApplicationUtil.applicationContext == null)
        {
            SpringApplicationUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public static Object getBean(String beanName)
    {
        return getApplicationContext().getBean(beanName);
    }

    public static <T> T getBean(Class<T> beanClass)
    {
        return getApplicationContext().getBean(beanClass);
    }

    public static <T> T getBean(String beanName, Class<T> beanClass)
    {
        return getApplicationContext().getBean(beanName, beanClass);
    }
}
