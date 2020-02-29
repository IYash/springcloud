package com.clnn.eurekaclient.service.impl;

import com.clnn.eurekaclient.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class DefaultHelloService implements HelloService {
    @Override
    public void sayHello(String code) {
        System.out.println("--------------------->"+code);
    }
}
