package com.clnn.pattern.controller;

import com.clnn.cuStarter.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloCon {
    @Autowired
    private HelloService helloService;

    @GetMapping("hello")
    public void sayHello(){
        helloService.sayHello();
    }
}
