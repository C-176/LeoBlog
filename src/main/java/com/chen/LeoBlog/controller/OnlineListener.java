package com.chen.LeoBlog.controller;

import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Controller
public class OnlineListener implements HttpSessionListener {
    private int count = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        count++;
        ServletContext servletContext = se.getSession().getServletContext();
        servletContext.setAttribute("count", count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        count--;
        ServletContext servletContext = se.getSession().getServletContext();
        servletContext.setAttribute("count", count);
    }
}
