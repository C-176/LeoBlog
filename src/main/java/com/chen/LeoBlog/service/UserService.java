package com.chen.LeoBlog.service;

import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.dao.UserDao;
import com.chen.LeoBlog.po.User;
import com.chen.LeoBlog.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;


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
        modelAndView.setViewName("back/info");
        return modelAndView;
    }


    public ResultInfo infoChange(Integer userId, Integer userSex, String userName, String userIntroduction, String userPos, String userPic, String userBirthday, String userIndustry, String userEducation, String userCertification, HttpSession session, HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        User user1 = getUserById(userId);

        //更新用户的修改信息
        if (userSex != null) {
            user1.setUserSex(userSex);

        }
        if (StringUtils.isNotBlank(userName)) {
            user1.setUserName(userName);
        }
        if (StringUtils.isNotBlank(userIntroduction)) {
            user1.setUserIntroduction(userIntroduction);
        }
        if (StringUtils.isNotBlank(userPos)) {
            user1.setUserPos(userPos);
        }
        if (StringUtils.isNotBlank(userPic)) {
            user1.setUserPic(userPic);
        }
        if (StringUtils.isNotBlank(userBirthday)) {
            user1.setUserBirthday(userBirthday);
        }
        if (StringUtils.isNotBlank(userIndustry)) {
            user1.setUserIndustry(userIndustry);
        }
        if (StringUtils.isNotBlank(userEducation)) {
            user1.setUserEducation(userEducation);
        }
        if (StringUtils.isNotBlank(userCertification)) {
            user1.setUserCertification(userCertification);
        }
        //调用service层的方法
        Integer changeNum = updateUser(user1);
        AssertUtil.isTrue(changeNum < 1, "修改信息失败");
        resultInfo.setCode(200);
        resultInfo.setMsg("修改信息成功");
        return resultInfo;
    }

}
