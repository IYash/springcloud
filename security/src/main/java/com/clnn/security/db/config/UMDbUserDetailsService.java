package com.clnn.security.db.config;

import com.clnn.security.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UMDbUserDetailsService extends CommonUserDetailsService {

    private final UserService userService;

    @Autowired
    UMDbUserDetailsService(UserService userService){
        this.userService = userService;
    }


    @Override
    public Object doSpecial() {
        System.out.println("UMDbUserDetailsService");
        return "UMDbUserDetailsService";
    }

    @Override
    public UserService getUserService() {
        return userService;
    }
}
