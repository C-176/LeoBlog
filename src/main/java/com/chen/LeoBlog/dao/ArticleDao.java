package com.chen.LeoBlog.dao;

import com.chen.LeoBlog.po.Article;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public interface ArticleDao {
    Article getArticleById(Integer articleId);

    ArrayList<Article> getAllArticle();

    ArrayList<Article> getArticleByKeyword(String keyword);

    ArrayList<Article> getArticleByUserId(Integer userId);

    Integer deleteArticleById(Integer articleId);

    Integer insertArticle(Article article);

    Integer changeArticle(Article article);


}
