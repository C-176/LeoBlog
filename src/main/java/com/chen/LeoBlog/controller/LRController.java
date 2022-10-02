package com.chen.LeoBlog.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.po.User;
import com.chen.LeoBlog.service.LRService;
import com.chen.LeoBlog.service.MailService;
import com.chen.LeoBlog.service.UserService;
import com.chen.LeoBlog.utils.CookieUtil;
import com.chen.LeoBlog.utils.UserIDBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
public class LRController {

    @Autowired
    private LRService lrService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    private LineCaptcha lineCaptcha;

    // 跳转到index
    @RequestMapping("/ToIndex")
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request, HttpSession session) {

        //从cookie中拿到userIdStr并解码
        Integer userId = UserIDBase64.decoderUserID(CookieUtil.getCookieValue(request, "userIdStr"));
        User user = userService.getUserById(userId);
        assert  user!=null;

        lrService.toLogin(modelAndView, session, user);
        return modelAndView;
    }

    /**
     * 登录功能
     */
    @PostMapping("/login1")
    @ResponseBody


    public ResultInfo login1(@RequestBody Map<String,String> map, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("接受/////");
        String userName = map.get("userName");
        String userPassword = map.get("userPassword");
        String captcha = map.get("captcha");
        return lrService.login(userName, userPassword, captcha,lineCaptcha,request, response);

    }

    /**
     * 登录功能
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultInfo login(String userName,String userPassword,String captcha, HttpServletRequest request, HttpServletResponse response) {
        return lrService.login(userName, userPassword, captcha,lineCaptcha,request, response);

    }

    /**
     * 注册功能
     * @return
     * @throws Exception
     */
    @PostMapping("/register")
    @ResponseBody
    public ResultInfo register(String userName, String userEmail, String userPassword, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return lrService.register(userName, userEmail, userPassword, request, response);
    }

    /**
     * 退出登陆
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/loginOut")
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        lrService.loginOut(request, response);
    }

    /**
     * 给指定邮箱发送验证邮件
     */
    @GetMapping("/emailSend/{email}")
    @ResponseBody
    public ResultInfo sendAndConfirm(@PathVariable String email){
        return lrService.sendAndConfirm(email);
    }

    /**
     * 对比验证码，激活邮箱
     */
    @PostMapping("/emailConfirm/{userEmail}/{code}")
    @ResponseBody
    public ResultInfo confirm(@PathVariable String code,@PathVariable String userEmail){
        if("".equals(code.trim())) return new ResultInfo(300,"验证码为空");
        int confirmCode = mailService.getConfirmCode(userEmail);
        if(confirmCode!=Integer.parseInt(code)) return new ResultInfo(300,"验证码错误，请重新验证");

        return new ResultInfo(200,"邮箱验证成功");
    }

    @RequestMapping("/getCaptcha")
    public  void getCode(HttpServletResponse response){
        //HuTool定义图形验证码的长和宽,验证码的位数，干扰线的条数
        lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36,4,20);
        StrUtil.format("Captcha: {}",lineCaptcha.getCode());
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            lineCaptcha.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
