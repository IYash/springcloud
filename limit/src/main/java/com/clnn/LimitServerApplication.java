package com.clnn;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class LimitServerApplication {

    public static void main( String[] args )
    {
        SpringApplication.run(LimitServerApplication.class,args);
    }

}
