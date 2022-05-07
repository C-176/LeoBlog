package com.chen.LeoBlog.po;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Component
public class Article implements Serializable {
    private Integer userId;
    private Integer articleId;
    private Date changedTime;
    private String comment;
    private String title;
    private String picUrl;
    private String articleUrl;
    private ArrayList<String> tagList;
    private String author;
    private int original;


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

    public ArrayList<String> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<String> tagList) {
        this.tagList = tagList;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Date getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(Date changedTime) {
        this.changedTime = changedTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", userId=" + userId +
                ", articleId=" + articleId +
                ", changedTime=" + changedTime +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
