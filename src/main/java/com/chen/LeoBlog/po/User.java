package com.chen.LeoBlog.po;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class User implements Serializable {
    private Integer identityId;
    private String userName;
    private Integer userId;
    private String userEmail;
    private String userPassword;
    private String userPic;
    private String userBirthday;
    private String userRegisterDay;
    private String userLoginDay;
    private Integer userSex;
    private String userIntroduction;
    private String userPos;
    //行业
    private String userIndustry;
    //教育经历
    private String userEducation;
    //个人认证
    private String userCertification;

    public String getUserIndustry() {
        return userIndustry;
    }

    public void setUserIndustry(String userIndustry) {
        this.userIndustry = userIndustry;
    }

    public String getUserEducation() {
        return userEducation;
    }

    public void setUserEducation(String userEducation) {
        this.userEducation = userEducation;
    }

    public String getUserCertification() {
        return userCertification;
    }

    public void setUserCertification(String userCertification) {
        this.userCertification = userCertification;
    }

    @Override
    public String toString() {
        return "User{" +
                "identityId=" + identityId +
                ", userName='" + userName + '\'' +
                ", userId=" + userId +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPic='" + userPic + '\'' +
                ", userBirthday='" + userBirthday + '\'' +
                ", userRegisterDay='" + userRegisterDay + '\'' +
                ", userLoginDay='" + userLoginDay + '\'' +
                ", userSex=" + userSex +
                ", userIntroduction='" + userIntroduction + '\'' +
                ", userPos='" + userPos + '\'' +
                '}';
    }

    public Integer getIdentityId() {
        return identityId;
    }

    public void setIdentityId(Integer identityId) {
        this.identityId = identityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserRegisterDay() {
        return userRegisterDay;
    }

    public void setUserRegisterDay(String userRegisterDay) {
        this.userRegisterDay = userRegisterDay;
    }

    public String getUserLoginDay() {
        return userLoginDay;
    }

    public void setUserLoginDay(String userLoginDay) {
        this.userLoginDay = userLoginDay;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserIntroduction() {
        return userIntroduction;
    }

    public void setUserIntroduction(String userIntroduction) {
        this.userIntroduction = userIntroduction;
    }

    public String getUserPos() {
        return userPos;
    }

    public void setUserPos(String userPos) {
        this.userPos = userPos;
    }
}
