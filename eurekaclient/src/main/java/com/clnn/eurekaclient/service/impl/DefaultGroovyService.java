package com.clnn.eurekaclient.service.impl;

import com.clnn.eurekaclient.service.GroovyService;
import groovy.lang.GroovyClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class DefaultGroovyService implements GroovyService{
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void initial2Groovy(String scriptContent) throws Exception{
        //编译
        Class clazz = new GroovyClassLoader().parseClass(scriptContent);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(beanDefinition, "hello");
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
        beanFactory.registerBeanDefinition("test",beanDefinition);
        clazz.getDeclaredMethod("run").invoke(applicationContext.getBean("test"));
        ((BeanDefinitionRegistry) applicationContext.getBeanFactory()).removeBeanDefinition("test");
    }


}
