package com.chen.LeoBlog.po;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EmailConfirm {
    private long id;
    private long userId;
    private int confirmCode;
    private String userEmail;
    private String confirmDate;
    private String confirmTime;

    public EmailConfirm(long id, long userId, int confirmCode, String userEmail, String confirmDate, String confirmTime) {
        this.id = id;
        this.userId = userId;
        this.confirmCode = confirmCode;
        this.userEmail = userEmail;
        this.confirmDate = confirmDate;
        this.confirmTime = confirmTime;
    }

    public EmailConfirm() {
    }


}
