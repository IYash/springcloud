package com.clnn.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 吐槽：
 * 一个项目可能会包含很多个服务，每个服务的端口和ip都可能不一样。
 * 那么如果以这种方式提供接口给外部调用，代价是非常大的。
 * 从安全性上考虑，系统对外提供的接口应该进行合法性校验，防止非法请求，如果按照
 * 这种形式，那每个服务都要写一遍校验规则，维护起来也很麻烦
 */
@RestController
public class HelloController {

    @Value("${server.port}")
    private int port;

    @GetMapping("index")
    public String index(){
        return "hello world,port:"+port;
    }
}
