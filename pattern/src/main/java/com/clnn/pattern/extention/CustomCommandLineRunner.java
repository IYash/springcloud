package com.clnn.pattern.extention;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.core.annotation.Order;

@Order
@SpringBootConfiguration
public class CustomCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("自定义run");
    }
}
