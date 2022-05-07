package com.chen.LeoBlog.controller;

import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.po.User;
import com.chen.LeoBlog.service.LRService;
import com.chen.LeoBlog.service.UserService;
import com.chen.LeoBlog.utils.CookieUtil;
import com.chen.LeoBlog.utils.UserIDBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller

public class LRController {

    @Autowired
    private LRService lrService;
    @Autowired
    private UserService userService;

    // 跳转到index
    @RequestMapping("/ToIndex")
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request, HttpSession session) {

        //从cookie中拿到userIdStr并解码
        Integer userId = UserIDBase64.decoderUserID(CookieUtil.getCookieValue(request, "userIdStr"));
        User user = userService.getUserById(userId);

        lrService.toLogin(modelAndView, session, user);
        return modelAndView;
    }

    /**
     * 登录功能
     *
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultInfo login(String userName, String userPassword, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("userName:" + userName + "userPassword:" + userPassword);
        return lrService.login(userName, userPassword, request, response);

    }

    @PostMapping("/register")
    @ResponseBody
    public ResultInfo register(String userName, String userEmail, String userPassword, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return lrService.register(userName, userEmail, userPassword, request, response);
    }

    @RequestMapping("/loginOut")
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        lrService.loginOut(request, response);
    }


}
