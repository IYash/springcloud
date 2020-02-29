package com.clnn.shiro.filter;

import com.clnn.comm.Constant;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class PermissionFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        System.out.println(Constant.BASE+"----------------------<<<<<<<<<<<<<<<<<");
        System.out.println("permssion Filter isAccessAllowed");
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        System.out.println("permission Filter onAccessDenied");
        return true;
    }
}
