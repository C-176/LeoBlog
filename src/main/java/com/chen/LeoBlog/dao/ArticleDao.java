package com.chen.LeoBlog.dao;

import com.chen.LeoBlog.po.Article;

import java.util.ArrayList;

public interface ArticleDao {
    Article getArticleById(Integer articleId);

    ArrayList<Article> getAllArticle();

    ArrayList<Article> getArticleByKeyword(String keyword);

    ArrayList<Article> getArticleByUserId(Integer userId);

    Integer deleteArticleById(Integer articleId);

    Integer insertArticle(Article article);

    Integer changeArticle(Article article);
    //Todo tags

}
