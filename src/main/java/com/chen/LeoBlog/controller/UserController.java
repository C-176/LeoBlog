package com.chen.LeoBlog.controller;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.po.ChatRecord;
import com.chen.LeoBlog.po.User;
import com.chen.LeoBlog.service.*;
import com.chen.LeoBlog.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private LRService lrService;
    @Autowired
    private ChatService chatService;


    /**
     * 展示个人信息
     */
    @GetMapping("/info/{userId}")
    public ModelAndView showMyInfo(ModelAndView modelAndView, @PathVariable Integer userId, HttpSession session) {

        return userService.showMyInfo(modelAndView, userId, session);
    }

    @GetMapping("/security/{userId}")
    public ModelAndView showSecurity(ModelAndView modelAndView, @PathVariable Integer userId, HttpSession session){
        modelAndView.setViewName("/back/security");
        return modelAndView;
    }

    /**
     * 修改个人信息
     */
    @PutMapping("/info/{userId}/change")
    @ResponseBody
    public ResultInfo infoChange(@PathVariable Integer userId, Integer userSex, String userName, String userIntroduction, String userPos, String userPic, String userBirthday, String userIndustry, String userEducation, String userCertification,String userEmail,String userPassword, HttpSession session, HttpServletRequest request) {
        return userService.infoChange(userId, userSex, userName, userIntroduction, userPos, userPic, userBirthday, userIndustry, userEducation, userCertification,userEmail,userPassword, session, request);
    }

    //TODO:找回密码
    @GetMapping("/security/{userEmail}/find")
    @ResponseBody
    public ResultInfo findPwd(@PathVariable String userEmail){
        //TODO:realize it!
        return lrService.sendAndConfirm(userEmail);
    }

    @RequestMapping("/getChatUserList/{userId}")
    @ResponseBody
    public String getUserList(@PathVariable Integer userId) {
        List<User> userList = userService.getChatUserList(userId);
        JSONArray jsonArray = new JSONArray();
        for (User user : userList) {
            List<ChatRecord> records = chatService.getRecordsBySenderIdOrReceiverId(userId, user.getUserId());
            JSONObject put;
            if(records.size() == 0) {
                put = JSONUtil.parseObj(user).put("create_datetime", "").put("content", "");

            }else{
                ChatRecord chatRecord = records.get(records.size() - 1);
                String create_datetime = chatRecord.getCreate_datetime();
                Date date = DateUtil.parse(create_datetime);
                create_datetime = DateUtil.formatBetween(date, DateUtil.date(), BetweenFormatter.Level.MINUTE);

                String content = chatRecord.getContent();
                put = JSONUtil.parseObj(user).put("create_datetime", create_datetime+"前").put("content", content);
            }
            jsonArray.add(put);
        }
        String s = JSONUtil.toJsonStr(jsonArray);
        return s;
    }


}
