package com.clnn.security.db.component;

import com.clnn.security.db.config.SpringSessionConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@SpringBootConfiguration
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityInitializer() {
        super(SecurityConfig.class, SpringSessionConfig.class);
        System.out.println("-------------------SecurityInitializer");
    }
}
