package com.clnn.security.db.controller;

import com.clnn.security.db.entity.UserDO;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *    //https://codeday.me/bug/20180818/220146.html
 */
@RestController
@RequestMapping("custom")
public class CustomController {


    @GetMapping("/vip/test")
    //@Secured({"ROLE_VIP","ROLE_USER"})// 需要ROLE_VIP权限可访问
    @PreAuthorize("hasAuthority('ROLE_VIP') or hasAuthority('ROLE_USER') and isAdmin()")
    public String vipPath() {
        return "仅 ROLE_VIP,ROLE_USER 可看";
    }

    @GetMapping("/vip")
    public boolean updateToVIP() {
        // 得到当前的认证信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //  生成当前的所有授权
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        // 添加 ROLE_VIP 授权
        updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_VIP"));
        // 生成新的认证信息
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        // 重置认证信息
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return true;
    }

    @GetMapping("/{uri}")
    public String bcd(@PathVariable String uri) {
        return uri;
    }



    //MethodSecurityInterceptor
    @PreAuthorize("#username == authentication.principal.username or hasAuthority('ADMIN')")
    @GetMapping("/fetchUsername")
    public String fetchUsername(@P("username") String username ){
        return username +" - username - info";
    }

    @PreAuthorize("#nickname == authentication.principal.nickname or hasAuthority('ADMIN')")
    @GetMapping("/fetchNickname")
    public String fetchNickname(@P("nickname") String nickname ){
        return nickname +" - nickname - info";
    }

    @PreAuthorize("isAdmin() or isOwner(#id)")
    @GetMapping("/{id}/doDelete")
    public void doDelete(@PathVariable int id) {
        System.out.println(this.getClass().getCanonicalName()+" doDelete ");
    }

    //这里似乎有个bug，通过get请求的话直接跳过了权限验证，这里的使用有点强大了，了解下security表达式解析实现的原理
    @PreAuthorize("isAdmin() or isOwner(#userDo.id,#userDo.username)") //这里注解方法可以继续拓展
    @PostMapping("/doUpdate")
    public void doUpdate(@RequestBody UserDO userDo){
        System.out.println(this.getClass().getCanonicalName()+" doUpdate 1234");
    }

    @GetMapping("/session")
    public void getSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(((HttpSession) session).getId());
    }
    //这里似乎有个bug，通过get请求的话直接跳过了权限验证，这里的使用有点强大了，了解下security表达式解析实现的原理
    @PreAuthorize("isAdmin() or isOwner(#userDo.id,#userDo.username)") //这里注解方法可以继续拓展
    @PostMapping("/doMap")
    public void doMap(@RequestParam Map<String,Object> map,UserDO userDo){
        System.out.println(this.getClass().getCanonicalName()+" doMap "+ map.get("id"));
    }
}
