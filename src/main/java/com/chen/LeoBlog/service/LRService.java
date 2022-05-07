package com.chen.LeoBlog.service;

import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.po.Article;
import com.chen.LeoBlog.po.Comment;
import com.chen.LeoBlog.po.Script;
import com.chen.LeoBlog.po.User;
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
import java.io.IOException;
import java.util.ArrayList;

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
//    @Autowired
//    private SimpleOrderManger simpleOrderManger;

    /**
     * 注册功能
     *
     * @param userName
     * @param userEmail
     * @param userPassword
     * @param request
     * @return
     */
    public ResultInfo register(String userName, String userEmail, String userPassword, HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("userName:" + userName);
        System.out.println("userEmail:" + userEmail);
        System.out.println("userPassword:" + userPassword);

        HttpSession session = request.getSession();
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不可为空！");
        AssertUtil.isTrue(StringUtils.isBlank(userEmail), "邮箱不可为空！");
        AssertUtil.isTrue(StringUtils.isBlank(userPassword), "密码不可为空！");
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

        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(200);
        resultInfo.setMsg("注册成功！");
        return resultInfo;
    }

    /**
     * 登录功能
     *
     * @return
     */
    public ResultInfo login(String userName, String userPassword, HttpServletRequest request, HttpServletResponse response) {
//        先校验一下前端过来的数据合法否
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不可为空！");
        AssertUtil.isTrue(StringUtils.isBlank(userPassword), "用户密码不可为空！");

        //应该是个接口，供前端调用。
        ResultInfo resultInfo = new ResultInfo();
        User user;
        //判断userName是否是邮箱格式,根据格式查询user。
        if (userName.contains("@")) {
            user = userService.getUserByEmail(userName);
        } else {
            user = userService.getUserByName(userName);
        }
        AssertUtil.isTrue(null == user, "账号还未注册");

        if (!user.getUserPassword().equals(userPassword)) {
            resultInfo.setCode(300);
            resultInfo.setMsg("密码错误！");
        } else {
            // 写入cookie
            String s = UserIDBase64.encoderUserID(user.getUserId());
            Cookie cookie = new Cookie("userIdStr", s);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return resultInfo;
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
        int[] random = new int[5];
        for (int i = 0; i < random.length; i++) {
            random[i] = (int) (Math.random() * 167) + 1;
        }
        modelAndView.addObject("randomList", random);
        modelAndView.setViewName("forward/index");
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
}
