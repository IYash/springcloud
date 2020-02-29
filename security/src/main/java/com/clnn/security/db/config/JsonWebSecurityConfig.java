package com.clnn.security.db.config;

import com.clnn.security.db.component.CustomAuthenticationProvider;
import com.clnn.security.db.component.UMCustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

/**
 * https://blog.csdn.net/Canon_in_D_Major/article/details/79688441可参考配置
 * https://blog.csdn.net/CHS007chs/article/details/81326294基于注解和标签的权限控制
 * securedEnabled基于角色，prePostEnabled基于表达式
 * https://www.cnblogs.com/lori/p/10400564.html自定义认证流程
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled = true)
public class JsonWebSecurityConfig extends WebSecurityConfigurerAdapter{

    private DbUserDetailsService dbUserDetailsService;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    private UMCustomAuthenticationProvider umCustomAuthenticationProvider;
    @Autowired
    public void setAnyUserDetailsService(DbUserDetailsService dbUserDetailsService){
        this.dbUserDetailsService = dbUserDetailsService;
    }
    @Autowired
    private AccessDeniedHandler myAccessDeniedHandler;

    //在这里配置哪些页面不需要认证
    @Override
    public void configure(WebSecurity web) throws Exception {
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
                //开启匿名访问
                .anonymous()
                //.authorities("ROLE_ANONYMOUS")
                .and()
                .authorizeRequests()
                .antMatchers("/**/*").permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler)
                .and()
                .formLogin()
                .and().csrf().disable();
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

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

    //注册自定义的UsernamePasswordAuthenticationFilter,用于支持json登陆
    //@Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setFilterProcessesUrl("/db/login");
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write("{\"status\":\"登陆成功\"}");
                out.flush();
                out.close();
            }
        });
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write("{\"status\":\"登陆失败\"}");
                out.flush();
                out.close();
            }
        });
        filter.setAuthenticationManager(authenticationManagerBean());

        return filter;
    }
    //这里涉及到要修改登陆参数的传递，故修改了UsernamePasswordAuthenticationFilter,UsernamePasswordAuthenticationToken,AuthenticationProvider
    //如果只是简单的支持验证码登陆的化可以添加一个验证码拦截器
    //建议不修改UsernamePasswordAuthenticationToken这个对象，其他额外的参数可以通过考虑其他的方式处理掉
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //return super.authenticationManagerBean();
        return new ProviderManager(Arrays.asList(customAuthenticationProvider,umCustomAuthenticationProvider));
    }


}
