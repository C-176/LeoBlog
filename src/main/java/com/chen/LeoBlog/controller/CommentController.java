package com.chen.LeoBlog.controller;

import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.po.Comment;
import com.chen.LeoBlog.service.ArticleService;
import com.chen.LeoBlog.service.CommentService;
import com.chen.LeoBlog.utils.AssertUtil;
import com.chen.LeoBlog.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/comments/mine/{senderId}")
    public ModelAndView showMyComments(ModelAndView modelAndView, @PathVariable int senderId, HttpSession session) {
        return commentService.showMyComments(modelAndView, senderId, session);
    }

    @RequestMapping("/comment/{commentToId}/new")
    @ResponseBody
    public ResultInfo addComment(int oneLevel, int articleId,@PathVariable int commentToId, int senderId, String content, HttpSession session) {
        int receiverId;
        if (commentToId==-1){
            receiverId = articleService.getArticleById(articleId).getUserId();
        }else{
            receiverId = commentService.getCommentById(commentToId).getSenderId();
        }
        ResultInfo resultInfo = new ResultInfo();
        Comment comment = new Comment();
        comment.setSenderId(senderId);
        comment.setReceiverId(receiverId);
        comment.setContent(content);
        comment.setArticleId(articleId);
        comment.setCommentId(Util.getId());
        comment.setChangedTime(new Date());
        comment.setOneLevel(oneLevel);
        comment.setCommentToId(commentToId);
        int i = commentService.insertComment(comment);
        AssertUtil.isTrue(i==0,"评论失败");

        resultInfo.setMsg("评论成功");
        resultInfo.setCode(200);
        return resultInfo;


    }

    @RequestMapping("/getOneLevelCommentsByArticleId0")
    @ResponseBody
    public Comment get1(int articleId) {
        Comment comment = commentService.getOneLevelCommentsByArticleId0(articleId).get(0);
        return comment;
    }

    @RequestMapping("/getOneLevelCommentsByArticleId")
    @ResponseBody
    public Comment get(int articleId) {
        Comment comment = commentService.getOneLevelCommentsByArticleId(articleId).get(0);
        return comment;
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseBody
    public ResultInfo deleteComment(@PathVariable int commentId,HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        int i = commentService.deleteComment(commentId);
        AssertUtil.isTrue(i==0,"删除失败");
        resultInfo.setMsg("删除成功");
        resultInfo.setCode(200);
        return resultInfo;
    }
    @PutMapping("/comment/{commentId}/change")
    @ResponseBody
    public ResultInfo changeComment(@PathVariable Integer commentId,String commentContent){
        ResultInfo resultInfo = new ResultInfo();
        int i = commentService.changeComment(commentContent,commentId,new Date());
        AssertUtil.isTrue(i==0,"评论修改失败");
        resultInfo.setCode(200);
        resultInfo.setMsg("评论修改成功");
        return resultInfo;
    }
}
