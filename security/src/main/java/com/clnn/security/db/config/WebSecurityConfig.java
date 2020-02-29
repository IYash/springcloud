package com.clnn.security.db.config;

import com.clnn.security.db.component.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * https://blog.csdn.net/Canon_in_D_Major/article/details/79688441可参考配置
 * https://blog.csdn.net/CHS007chs/article/details/81326294基于注解和标签的权限控制
 * securedEnabled基于角色，prePostEnabled基于表达式
 */
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private DbUserDetailsService dbUserDetailsService;

    @Autowired
    public void setAnyUserDetailsService(DbUserDetailsService dbUserDetailsService){
        this.dbUserDetailsService = dbUserDetailsService;
    }
    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;
    //根据一个url请求，获得访问它所需要的roles权限
    @Autowired
    FilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;

    //接收一个用户的信息和访问一个url所需要的权限，判断该用户是否可以访问
    @Autowired
    AccessDecisionManager myAccessDecisionManager;
    //在这里配置哪些页面不需要认证
    @Override
    public void configure(WebSecurity web) throws Exception {
       // System.setProperty("spring.security.strategy",strategyName);
        web.ignoring().antMatchers("/", "/noAuthenticate");
    }


    /**
     * 匹配 "/" 路径，不需要权限即可访问
     * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
     * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
     * 默认启用 CSRF
     * 登陆的逻辑很奇特，是post请求，postman模拟时却只能放在url上
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//配置安全策略
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(myAccessDecisionManager);
                        return o;
                    }
                })
                .anyRequest()
                .authenticated()
                .antMatchers("/**/*").permitAll()
                .antMatchers("/db/user/**").hasAuthority("USER")
                .and()
                .formLogin().loginPage("/db/login").defaultSuccessUrl("/db/user")
                .and()
                .logout().logoutUrl("/db/logout").logoutSuccessUrl("/db/login")
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);
    }

    /**
     * 添加 UserDetailsService， 实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(dbUserDetailsService);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
