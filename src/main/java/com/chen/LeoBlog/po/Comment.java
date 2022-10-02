package com.chen.LeoBlog.po;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Data
public class Comment implements Serializable {
    private Integer commentId;
    private Integer receiverId;
    private String content;
    private Integer senderId;
    private Integer articleId;
    private Date changedTime;
    private Integer oneLevel;
    private Integer commentToId;


}
