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


        return new ResultInfo(200,"评论成功");


    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseBody
    public ResultInfo deleteComment(@PathVariable int commentId,HttpServletRequest request) {

        int i = commentService.deleteComment(commentId);
        AssertUtil.isTrue(i==0,"删除失败");

        return new ResultInfo(200,"删除成功");
    }
    @PutMapping("/comment/{commentId}/change")
    @ResponseBody
    public ResultInfo changeComment(@PathVariable Integer commentId,String commentContent){

        int i = commentService.changeComment(commentContent,commentId,new Date());
        AssertUtil.isTrue(i==0,"修改失败");

        return new ResultInfo(200,"修改成功");
    }
}
