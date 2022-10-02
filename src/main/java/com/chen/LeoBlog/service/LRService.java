package com.chen.LeoBlog.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.controller.LRController;
import com.chen.LeoBlog.po.*;
import com.chen.LeoBlog.utils.AssertUtil;
import com.chen.LeoBlog.utils.UserIDBase64;
import com.chen.LeoBlog.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class LRService {

    @Autowired
    public UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ScriptService scriptService;
    @Autowired
    private MailService mailService;

//    @Autowired
//    private SimpleOrderManger simpleOrderManger;


    /**
     * 注册功能
     */
    public ResultInfo register(String userName, String userEmail, String userPassword, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        AssertUtil.isTrue(StrUtil.isBlank(userName), "用户名不可为空！");
        AssertUtil.isTrue(StrUtil.isBlank(userEmail), "邮箱不可为空！");
        AssertUtil.isTrue(StrUtil.isBlank(userPassword), "密码不可为空！");
        AssertUtil.isTrue(userName.length()<3,"用户名长度不能小于3位！");
        AssertUtil.isTrue(userPassword.length()<6,"密码长度不能小于6位！");
        AssertUtil.isTrue(null != userService.getUserByEmail(userEmail), "该邮箱已被注册！");
        AssertUtil.isTrue(null != userService.getUserByName(userName), "该用户名已被注册！");

        User user = new User();
        user.setUserEmail(userEmail);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setUserId(Util.getId());

        AssertUtil.isTrue(userService.insertUser(user) < 1, "注册失败！");
        User newUser = userService.getUserByEmail(userEmail);
        session.setAttribute("user", newUser);

        return new ResultInfo(200,"注册成功！");
    }

    /**
     * 登录功能
     */
    public ResultInfo login(String userName, String userPassword,String captcha, LineCaptcha lineCaptcha,HttpServletRequest request, HttpServletResponse response) {
//        先校验一下前端过来的数据合法否
        AssertUtil.isTrue(StrUtil.isBlank(userName), "用户名不可为空！");
        AssertUtil.isTrue(StrUtil.isBlank(userPassword), "用户密码不可为空！");

        AssertUtil.isTrue(!lineCaptcha.verify(captcha),"验证码错误");
        User user;
        //判断userName是否是邮箱格式,根据格式查询user。
        if (StrUtil.contains(userName,"@")) {
            user = userService.getUserByEmail(userName);
        } else {
            user = userService.getUserByName(userName);
        }
        AssertUtil.isTrue(null == user, "账号还未注册");

        assert user != null;
        if (!user.getUserPassword().equals(userPassword)) {
            return new ResultInfo(300,"密码错误");
        } else {
            // 写入cookie
            String s = UserIDBase64.encoderUserID(user.getUserId());
            Cookie cookie = new Cookie("userIdStr", s);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return new ResultInfo(200,"操作成功");
    }

    //    去登陆
    public void toLogin(ModelAndView modelAndView, HttpSession session, User user) {

        // 将session周期设置为直到关闭浏览器
        session.setMaxInactiveInterval(60 * 60 * 24);
        session.setAttribute("user", user);
        modelAndView.addObject("user", user);
        ArrayList<Article> allArticle = articleService.getAllArticle();
        modelAndView.addObject("articleList", allArticle);
        ArrayList<Article> articleByUserId = articleService.getArticleByUserId(user.getUserId());
        modelAndView.addObject("myArticlesSize", articleByUserId.size());

        ArrayList<Comment> commentsByUserId = commentService.getCommentsBySenderId(user.getUserId());
        modelAndView.addObject("myCommentsSize", commentsByUserId.size());
        ArrayList<Script> scriptByUserId = scriptService.getScriptByUserId(user.getUserId());
        modelAndView.addObject("myScriptsSize", scriptByUserId.size());
        //获取一个5个1到167之间随机整数的整数数组
        Integer[] random = Stream.generate(Math::random).limit(5).map(s-> (int) (s * 167+1)).toArray(Integer[]::new);
        modelAndView.addObject("randomList", random);
        modelAndView.setViewName("/forward/index");
    }

    /**
     * 退出登录
     */
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //清空session中的user信息
        session.invalidate();

//        清除cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

        }
//        CookieUtil.deleteCookie("userIdStr",request,response);
//        CookieUtil.deleteCookie("loginOrRegister",request,response);
//        CookieUtil.deleteCookie("user",request,response);
        response.sendRedirect("index.html");
    }


    public ResultInfo sendAndConfirm(String to){
        ResultInfo resultInfo = new ResultInfo();

        String subject = "LeoBlog 邮箱验证信息";
        String content = Util.getConfirmCode()+"";
        try{
            mailService.sendSimpleMail(to,subject,content);

            int userId = Util.getId();
            int confirmCode = Util.getConfirmCode();
            String s = DateUtil.formatDateTime(new Date());

            EmailConfirm emailConfirm = new EmailConfirm(Util.getId(), userId, confirmCode, to, s.substring(0, 10), s.substring(11));
            if(mailService.getConfirmCodeLen(to) != 0) {
                int i = mailService.deleteCode(emailConfirm);
                AssertUtil.isTrue(i==0,"验证码删除失败");
            }
            int changeNumber = mailService.sendConfirmCode(emailConfirm);
            if(changeNumber==0) return new ResultInfo(300,"插入数据失败");

            resultInfo.setMsg("验证码已发送，请查收。");
            resultInfo.setCode(200);

        }catch (Exception e) {
            resultInfo.setCode(300);
            resultInfo.setMsg("验证码发送失败，请稍后重试。");
        }


        return resultInfo;
    }
}
