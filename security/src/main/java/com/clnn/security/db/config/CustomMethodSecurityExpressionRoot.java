package com.clnn.security.db.config;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {



    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }
    private Object filterObject;
    private Object returnObject;
    private Object target;

    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    public Object getFilterObject() {
        return this.filterObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public Object getReturnObject() {
        return this.returnObject;
    }

    void setThis(Object target) {
        this.target = target;
    }

    public Object getThis() {
        return this.target;
    }
    public boolean isAdmin() {
        System.out.println(this.getClass().getCanonicalName()+"----->false");
       return false;
    }

    public boolean isOwner(String id,String username) {
        System.out.println(this.getClass().getCanonicalName()+"------>"+id+"-------->"+username);
        return true;
    }

}
