package com.chen.LeoBlog.po;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Component
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

    public String getScriptUrl() {
        return scriptUrl;
    }

    public void setScriptUrl(String scriptUrl) {
        this.scriptUrl = scriptUrl;
    }

    public ArrayList<String> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<String> tagList) {
        this.tagList = tagList;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Script{" +
                "userId=" + userId +
                ", scriptId=" + scriptId +
                ", changedTime=" + changedTime +
                ", comment='" + comment + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScriptId() {
        return scriptId;
    }

    public void setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
    }

    public Date getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(Date changedTime) {
        this.changedTime = changedTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
