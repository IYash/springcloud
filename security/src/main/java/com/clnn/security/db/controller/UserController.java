package com.clnn.security.db.controller;

import com.clnn.security.db.config.CustomUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller("dbUserController")
@RequestMapping("db")
public class UserController {

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal Principal principal, Model model){
        model.addAttribute("username", principal.getName());
        return "user/user";
    }
    //自动注入测试
    @GetMapping("/userC")
    public String userC(@AuthenticationPrincipal CustomUser cUser, Model model){
        model.addAttribute("username", cUser.getUsername());
        return "user/user";
    }

}