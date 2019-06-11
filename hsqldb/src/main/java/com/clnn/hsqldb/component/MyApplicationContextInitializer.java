package com.clnn.hsqldb.component;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 企图用于修改配置文件
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String SPRING_APPLICATION_NAME = "spring.application.name";

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        String defaultVal =  configurableApplicationContext.getEnvironment().getProperty(SPRING_APPLICATION_NAME);
        System.out.println("---------------------"+defaultVal);
        ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        environment.getSystemProperties().put(SPRING_APPLICATION_NAME,"HSQLSERVERD");

    }
}
