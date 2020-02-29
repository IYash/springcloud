package com.clnn.security.db.controller;

import com.clnn.security.db.entity.UserDO;
import com.clnn.security.db.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("dbHomeController")
@AllArgsConstructor
@RequestMapping("db")
public class HomeController {

    private final UserService userService;

    @GetMapping({"/", "/index", "/home"})
    public String root(){
        return "/index";
    }

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/register")
    public String register(){
        return "/register";
    }

    @PostMapping("/register")
    public String doRegister(UserDO userDO){
        // 此处省略校验逻辑
        userService.insert(userDO);
        return "redirect:register?success";
    }

}
