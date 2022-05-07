package com.chen.LeoBlog.aop;


import com.chen.LeoBlog.annotation.RequiredPermission;
import com.chen.LeoBlog.exception.NoLoginException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@Aspect
public class PermissionProxy {

    @Resource
    private HttpSession session;


    @Around(value = "@annotation(com.chen.LeoBlog.annotation.RequiredPermission)")
    public Object around(ProceedingJoinPoint pjp) {
        //判断用户是否登录
        List<String> permissions = (List<String>) session.getAttribute("permissions");
        if (permissions == null || permissions.size() == 0) {
            throw new NoLoginException("未登录异常");
        }
        //获取当前用户的拥有的权限码+目标方法头上权限码
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        RequiredPermission requiredPermission = methodSignature.getMethod().getDeclaredAnnotation(RequiredPermission.class);
        //判断
        if (!(permissions.contains(requiredPermission.code()))) {
            throw new NoLoginException("权限不足");
        }
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

}
