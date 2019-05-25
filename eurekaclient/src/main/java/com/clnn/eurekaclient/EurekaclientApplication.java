package com.clnn.eurekaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * SpringCloudApplication注解包含了EnableDiscoveryClient注解，无需现实开启服务发现
 */
@SpringCloudApplication
public class EurekaclientApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaclientApplication.class,args);
    }
}
