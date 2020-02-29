package com.clnn.shiro.controller;

import com.clnn.comm.Constant;
import com.clnn.shiro.entity.LoginForm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {


    @RequestMapping(value = {"/login", "/login.html"})
    public String loginTemplate() {
        return "login";
    }

    @PostMapping(value = {"/loginForm"})
    @ResponseBody
    public String login(@RequestBody LoginForm loginForm) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken upt = new UsernamePasswordToken(loginForm.getUsername(), loginForm.getPassword());
        subject.login(upt);
        boolean userC = subject.isPermitted("user:create");
        return userC+"";
    }
    @RequiresPermissions("user:create1")
    @PostMapping(value = {"/createForm"})
    @ResponseBody
    public String creatForm(@RequestBody LoginForm loginForm){
        return "success";
    }
}