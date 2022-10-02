package com.chen.LeoBlog.po;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
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
}
