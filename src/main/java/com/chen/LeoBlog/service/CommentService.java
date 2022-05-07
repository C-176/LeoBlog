package com.chen.LeoBlog.service;

import com.chen.LeoBlog.dao.CommentDao;
import com.chen.LeoBlog.po.Article;
import com.chen.LeoBlog.po.Comment;
import com.chen.LeoBlog.utils.AssertUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ArticleService articleService;


    public Comment getCommentById(int commentId) {
        return commentDao.getCommentById(commentId);
    }

    public ArrayList<Comment> getCommentsByToId(Integer commentToId){
        return commentDao.getCommentsByToId(commentToId);
    }

    public ArrayList<Comment> getCommentsBySenderId(int senderId) {
        return commentDao.getCommentsBySenderId(senderId);
    }


    public ArrayList<Comment> getCommentsByReceiverId(int receiverId) {
        return commentDao.getCommentsByReceiverId(receiverId);
    }

    public ArrayList<Comment> getOneLevelCommentsByArticleId0(int articleId) {
        return commentDao.getOneLevelCommentsByArticleId(articleId);
    }

    public ArrayList<Comment> getOneLevelCommentsByArticleId(int articleId) {
        return commentDao.getOneLevelCommentsByArticleId(articleId);
    }

    public ArrayList<Comment> getTwoLevelCommentsBySenderIdAndReceiverId(int senderId, int receiverId, int articleId) {
        return commentDao.getTwoLevelCommentsBySenderIdAndReceiverId(senderId, receiverId, articleId);
    }

    public int insertComment(Comment comment) {
        return commentDao.insertComment(comment);
    }

    public int deleteComment(int commentId) {
        return commentDao.deleteComment(commentId);
    }


    public int deleteCommentByArticleId(int articleId) {
        return commentDao.deleteCommentByArticleId(articleId);
    }

    public int deleteCommentBySenderId(int senderId) {
        return commentDao.deleteCommentBySenderId(senderId);
    }

    public int deleteCommentByReceiverId(int receiverId) {
        return commentDao.deleteCommentByReceiverId(receiverId);
    }

    public int changeComment(String content, int commentId, Date date) {
        return commentDao.changeComment(content,commentId,date);
    }

    public ModelAndView showMyComments(ModelAndView modelAndView, int senderId, HttpSession session) {
        ArrayList<Comment> comments = commentDao.getCommentsBySenderId(senderId);
        ArrayList<Article> articles = new ArrayList<>();
        for (Comment comment : comments) {
            Article article = articleService.getArticleById(comment.getArticleId());
//            AssertUtil.isTrue(article == null, "文章id为["+comment.getArticleId()+"]的文章不存在");
            if (article == null) {
                deleteCommentByArticleId(comment.getArticleId());
                comments.remove(comment);

            }else {
                articles.add(article);
            }
        }

        modelAndView.addObject("myComments", comments);

        System.out.println(comments.size()+"=========================================");
        System.out.println(articles.size()+"=========================================");
        modelAndView.addObject("articlesForComments", articles);
        modelAndView.setViewName("back/comment");
        return modelAndView;
    }
}
