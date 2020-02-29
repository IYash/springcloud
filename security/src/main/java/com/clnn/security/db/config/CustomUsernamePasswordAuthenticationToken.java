package com.clnn.security.db.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 5997779137686488796L;

    private String vcode;

    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials,String vcode) {
        super(principal,credentials);
        this.vcode = vcode;
    }
    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,String vcode) {
        super(principal,credentials,authorities);
        this.vcode = vcode;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}
