package com.chen.LeoBlog.po;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class Comment implements Serializable {
    private Integer commentId;
    private Integer receiverId;
    private String content;
    private Integer senderId;
    private Integer articleId;
    private Date changedTime;
    private Integer oneLevel;
    private Integer commentToId;

    public Integer getCommentToId() {
        return commentToId;
    }

    public void setCommentToId(Integer commentToId) {
        this.commentToId = commentToId;
    }

    public Integer getOneLevel() {
        return oneLevel;
    }

    public void setOneLevel(Integer oneLevel) {
        this.oneLevel = oneLevel;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
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

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", receiverId=" + receiverId +
                ", content='" + content + '\'' +
                ", senderId=" + senderId +
                ", articleId=" + articleId +
                ", changedTime=" + changedTime +
                ", oneLevel=" + oneLevel +
                '}';
    }
}
