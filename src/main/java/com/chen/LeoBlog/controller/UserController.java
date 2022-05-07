package com.chen.LeoBlog.controller;

import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.service.UploadService;
import com.chen.LeoBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UploadService uploadService;

    /**
     * 展示个人信息
     *
     * @param modelAndView
     * @return
     */
    @GetMapping("/info/{userId}")
    public ModelAndView showMyInfo(ModelAndView modelAndView, @PathVariable Integer userId, HttpSession session) {

        return userService.showMyInfo(modelAndView, userId, session);
    }

    /**
     * 修改个人信息
     *
     * @param userId
     * @param userSex
     * @param userName
     * @param userIntroduction
     * @param userPos
     * @param userPic
     * @param userBirthday
     * @param userIndustry
     * @param userEducation
     * @param userCertification
     * @param session
     * @param request
     * @return
     */
    @PutMapping("/info/{userId}/change")
    @ResponseBody
    public ResultInfo infoChange(@PathVariable Integer userId, Integer userSex, String userName, String userIntroduction, String userPos, String userPic, String userBirthday, String userIndustry, String userEducation, String userCertification, HttpSession session, HttpServletRequest request) {
        return userService.infoChange(userId, userSex, userName, userIntroduction, userPos, userPic, userBirthday, userIndustry, userEducation, userCertification, session, request);
    }


}
