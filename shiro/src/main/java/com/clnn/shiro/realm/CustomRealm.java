package com.clnn.shiro.realm;

import org.apache.catalina.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {

    public void setName(String name) {
        super.setName(name);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 从 principals获取主身份信息
        // 将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
        String userCode = (String) principalCollection.getPrimaryPrincipal();

        // 根据身份信息获取权限信息
        // 连接数据库...
        // 模拟从数据库获取到数据
        List<String> permissions = new ArrayList<String>();
        permissions.add("user:create");// 用户的创建
        permissions.add("items:add");// 商品添加权限
        // ....

        // 查到权限数据，返回授权信息(要包括 上边的permissions)
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 将上边查询到授权信息填充到simpleAuthorizationInfo对象中
        simpleAuthorizationInfo.addStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // token是用户输入的
        // 第一步从token中取出身份信息（token代表用户输入的传下来的信息）
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userCode = token.getUsername();

        // 第二步：根据用户输入的userCode从数据库查询
        // ....从数据库查数据

        // 如果查询不到返回null
        // 数据库中用户账号是zhangsansan

         if(!userCode.equals("zhangsansan")){ return null; }
         String password = new String(token.getPassword());
         if(!password.equals("1111111")){return null;}
        // 如果查询到返回认证信息AuthenticationInfo
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userCode, password,
                this.getName());

        return simpleAuthenticationInfo;
    }
}
