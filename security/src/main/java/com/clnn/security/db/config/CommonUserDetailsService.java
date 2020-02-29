package com.clnn.security.db.config;

import com.clnn.security.db.entity.UserDO;
import com.clnn.security.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public abstract class CommonUserDetailsService implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        doSpecial();
        UserDO userDO = getUserService().getByUsername(username);
        if (userDO == null){
            //throw new UsernameNotFoundException("用户不存在！");
            return null;
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("/db/user"));
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("/custom/vip/test"));
        //simpleGrantedAuthorities.add(new SimpleGrantedAuthority("/custom/vip"));
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("/custom/abc"));
       // return new org.springframework.security.core.userdetails.User(userDO.getUsername(), userDO.getPassword(), simpleGrantedAuthorities);
        return new CustomUser(userDO.getUsername(),userDO.getPassword(),simpleGrantedAuthorities,userDO.getNickname());
    }
    public abstract Object doSpecial();
    public abstract UserService getUserService();
}
