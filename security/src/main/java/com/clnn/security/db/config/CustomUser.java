package com.clnn.security.db.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


/**
 * 自定义Principal
 */
@Data
public class CustomUser extends User {

    private String nickname;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String nickname) {
        super(username, password, authorities);
        this.nickname = nickname;
    }

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String nickname) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.nickname = nickname;
    }
}
