package com.chen.LeoBlog.po;

import java.io.Serializable;

public class Tag implements Serializable {
    private String tag;
    private Integer articleId;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
