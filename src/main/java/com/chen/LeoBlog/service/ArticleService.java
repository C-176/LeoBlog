package com.chen.LeoBlog.service;

import cn.hutool.core.date.DateUtil;
import com.chen.LeoBlog.dao.ArticleDao;
import com.chen.LeoBlog.po.Article;
import com.chen.LeoBlog.po.Comment;
import com.chen.LeoBlog.po.Script;
import com.chen.LeoBlog.po.User;
import com.chen.LeoBlog.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@Transactional
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ScriptService scriptService;

    public Article getArticleById(Integer articleId) {
        return articleDao.getArticleById(articleId);
    }

    public ArrayList<Article> getArticleByKeyword(String keyword) {
        return articleDao.getArticleByKeyword(keyword);
    }

    public ArrayList<Article> getArticleByUserId(Integer userId) {
        return articleDao.getArticleByUserId(userId);
    }

    public Integer deleteArticleById(Integer articleId) {
        return articleDao.deleteArticleById(articleId);
    }

    public int insertArticle(Article article) {
        return articleDao.insertArticle(article);
    }

    public int changeArticle(Article article) {
        return articleDao.changeArticle(article);
    }

    public ArrayList<Article> getAllArticle() {
        return articleDao.getAllArticle();
    }

    public ModelAndView showAllArticles(ModelAndView modelAndView) {
        ArrayList<Article> allArticle = getAllArticle();
        modelAndView.addObject("articleList", allArticle);
        modelAndView.setViewName("/forward/article");
        return modelAndView;
    }

    public ModelAndView showMyArticles(ModelAndView modelAndView, HttpSession session, int userId) {

        ArrayList<Article> allArticle = getArticleByUserId(userId);
        modelAndView.addObject("myArticles", allArticle);

        modelAndView.setViewName("/back/article");
        return modelAndView;
    }


    public ModelAndView searchArticles(ModelAndView modelAndView, HttpSession session, String keyword) {
        ArrayList<Article> articleByKeyword = getArticleByKeyword(keyword);
        modelAndView.addObject("articleList", articleByKeyword);
        modelAndView.addObject("user", session.getAttribute("user"));

        modelAndView.setViewName("/forward/article");
        return modelAndView;
    }

    public ModelAndView showArticle(ModelAndView modelAndView, Integer articleId) {

        Article article = getArticleById(articleId);
        Script script = scriptService.getScriptById(articleId);
        if (article == null && script != null) {
            String format = DateUtil.formatDateTime(script.getChangedTime());
//            String format = Util.timeFormat(script.getChangedTime());
            modelAndView.addObject("article", script);
            modelAndView.addObject("user", userService.getUserById(script.getUserId()));
            modelAndView.addObject("timeFormat", format);
        } else if (article != null) {
            String format = DateUtil.formatDateTime(article.getChangedTime());
            modelAndView.addObject("article", article);
            modelAndView.addObject("user", userService.getUserById(article.getUserId()));
            modelAndView.addObject("timeFormat", format);
        }
        //一级评论
        ArrayList<Comment> oneLevelComments = commentService.getOneLevelCommentsByArticleId(articleId);
        //一级评论者
        List<User> oneLevelSenders = oneLevelComments.stream().map(s -> userService.getUserById(s.getSenderId())).collect(Collectors.toList());

        //二级评论
        ArrayList<List<Comment>> twoLevelComments = new ArrayList<>(oneLevelComments.size());
        ArrayList<List<User>> twoLevelSenders = new ArrayList<>(oneLevelComments.size());
        for (Comment comment:oneLevelComments){
            //根据一级标题的id获取二级评论
            List<Comment> commentsByToId = commentService.getCommentsByToId(comment.getCommentId());
            //二级评论者
            List<User> sendersByToId = commentsByToId.stream().map(s -> userService.getUserById(s.getSenderId())).collect(Collectors.toList());

            twoLevelSenders.add(sendersByToId);
            twoLevelComments.add(commentsByToId.size() == 0 ? new ArrayList<>() : commentsByToId);

        }

        modelAndView.addObject("oneLevelComments", oneLevelComments);
        modelAndView.addObject("oneLevelSenders", oneLevelSenders);
        modelAndView.addObject("twoLevelComments", twoLevelComments);
        modelAndView.addObject("twoLevelSenders", twoLevelSenders);
//        System.out.println(oneLevelComments.size()+" "+oneLevelSenders.size()+" "+twoLevelComments.size()+" "+twoLevelSenders.size());
        modelAndView.setViewName("/forward/showArticle");





        return modelAndView;
    }
}
