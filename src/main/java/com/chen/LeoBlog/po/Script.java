package com.chen.LeoBlog.po;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Component
@Data
public class Script implements Serializable {
    private Integer userId;
    private Integer scriptId;
    private Date changedTime;
    private String comment;
    private String title;
    private String picUrl;
    private String scriptUrl;
    private ArrayList<String> tagList;
    private String author;
    private int original;


}
