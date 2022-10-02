package com.chen.LeoBlog.service;

import cn.hutool.core.util.StrUtil;
import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.dao.UserDao;
import com.chen.LeoBlog.po.User;
import com.chen.LeoBlog.utils.AssertUtil;
import com.chen.LeoBlog.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserService {


    @Autowired
    //用于发送文件
    private MailService mailService;

    @Autowired
    private UserDao userDao;

    public  List<User> getChatUserList(Integer userId) {
        return userDao.getChatUserList(userId);
    }


    public User getUserByName(String userName) {
        return userDao.getUserByName(userName);
    }

    public User getUserByEmail(String Email) {
        return userDao.getUserByEmail(Email);
    }

    public Integer insertUser(User user) {
        return userDao.insertUser(user);
    }

    public Integer deleteUserById(Integer userId) {
        return userDao.deleteUserById(userId);
    }

    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    public Integer updateUser(User user) {
        return userDao.updateUser(user);
    }

    public ModelAndView showMyInfo(ModelAndView modelAndView, Integer userId, HttpSession session) {
        User user = getUserById(userId);
        session.setAttribute("user", user);
        modelAndView.setViewName("/back/info");
        return modelAndView;
    }


    public ResultInfo infoChange(Integer userId, Integer userSex, String userName, String userIntroduction, String userPos, String userPic, String userBirthday, String userIndustry, String userEducation, String userCertification,String userEmail,String userPassword, HttpSession session, HttpServletRequest request) {

        User user1 = getUserById(userId);

        //更新用户的修改信息
        if (userSex!=null) {
            user1.setUserSex(userSex);
        }
        if (StrUtil.isNotBlank(userName)) {
            user1.setUserName(userName);
        }
        if (StrUtil.isNotBlank(userIntroduction)) {
            user1.setUserIntroduction(userIntroduction);
        }
        if (StrUtil.isNotBlank(userPos)) {
            user1.setUserPos(userPos);
        }
        if (StrUtil.isNotBlank(userPic)) {
            user1.setUserPic(userPic);
        }
        if (StrUtil.isNotBlank(userBirthday)) {
            user1.setUserBirthday(userBirthday);
        }
        if (StrUtil.isNotBlank(userIndustry)) {
            user1.setUserIndustry(userIndustry);
        }
        if (StrUtil.isNotBlank(userEducation)) {
            user1.setUserEducation(userEducation);
        }
        if (StrUtil.isNotBlank(userCertification)) {
            user1.setUserCertification(userCertification);
        }
        if(StrUtil.isNotBlank(userEmail)){
            user1.setUserEmail(userEmail);
        }
        if(StrUtil.isNotBlank(userPassword)){
            user1.setUserPassword(userPassword);
        }
//        System.out.println(user1);
        //调用service层的方法
        Integer changeNum = updateUser(user1);
        AssertUtil.isTrue(changeNum < 1, "修改信息失败");
        session.setAttribute("user",user1);
        return new ResultInfo(200,"修改信息成功");
    }


}
