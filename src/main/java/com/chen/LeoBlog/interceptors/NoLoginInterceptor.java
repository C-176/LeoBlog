package com.chen.LeoBlog.interceptors;


import com.chen.LeoBlog.exception.NoLoginException;
import com.chen.LeoBlog.service.UserService;
import com.chen.LeoBlog.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired(required = false)
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //用户未登录，抛异常
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //判断用户是否登录
        if (-1 == userId || null == userService.getUserById(userId)) {
            throw new NoLoginException("用户尚未登录");
        }
        //放行
        return true;
    }
}
