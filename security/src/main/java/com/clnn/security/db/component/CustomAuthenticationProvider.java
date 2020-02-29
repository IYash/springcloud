package com.clnn.security.db.component;

import com.clnn.security.db.config.DbUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider extends CommonCustomAuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DbUserDetailsService userDetailsService;


    public CustomAuthenticationProvider() {
    }

    @Override
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }


    @Override
    public DbUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

}
