package com.clnn.security.db.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class CustomAspect {
    //Controller层切点
    @Pointcut("execution (* com.clnn.security.*.controller..*.*(..))")
    public  void controllerAspect() {
     }

    @Before("controllerAspect()")
     public void doBefore(JoinPoint joinPoint) throws Exception {
        Object target = joinPoint.getTarget();
        String methodName = joinPoint.getSignature().getName();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        Method method=target.getClass().getMethod(methodName,parameterTypes);
        if(method != null && method.isAnnotationPresent(RequirePermission.class)){
            System.out.println("==========执行controller前置通知===============");
        }
    }
}
