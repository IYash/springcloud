package com.clnn.security.mem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller("memHomeController")
public class HomeController {

    @GetMapping({"/", "/index", "/home"})
    public String root(){
        return "/index";
    }

    @GetMapping("/login")
    public String login(){
        return "/login";
    }
}
