package com.clnn.security.db.component;

import com.clnn.security.db.config.DbUserDetailsService;
import com.clnn.security.db.config.UMDbUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UMCustomAuthenticationProvider extends CommonCustomAuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UMDbUserDetailsService userDetailsService;


    public UMCustomAuthenticationProvider() {
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }


    @Override
    public UMDbUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

}
