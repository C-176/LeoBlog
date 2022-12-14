package com.chen.LeoBlog.controller;

import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.service.ArticleService;
import com.chen.LeoBlog.service.CommentService;
import com.chen.LeoBlog.utils.AssertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    /**
     * 从所有的文章中取出20个文章，并跳转到文章列表页面
     */

    
    @ApiOperation("从所有的文章中取出20个文章，并跳转到文章列表页面")
    @RequestMapping("/articles/publish")
    public ModelAndView showAllArticles(ModelAndView modelAndView) {
        return articleService.showAllArticles(modelAndView);
    }

    /**
     * 显示我的所有文章，并且更新到session中，并跳转到我的文章列表页面
     */
    @GetMapping("/articles/mine/{userId}")
    public ModelAndView showMyArticles(ModelAndView modelAndView, HttpSession session, @PathVariable int userId) {
        return articleService.showMyArticles(modelAndView, session, userId);
    }

    /**
     * 根据关键字查询文章，并跳转到文章列表页面
     */
    @GetMapping("/articles/publish/{keyword}")
    public ModelAndView searchArticles(ModelAndView modelAndView, HttpSession session, @PathVariable String keyword) {
        return articleService.searchArticles(modelAndView, session, keyword);
    }

    /**
     * 跳转到编辑文章页面
     */
    @RequestMapping("/article/{articleId}/{editorMode}")
    public ModelAndView editor(ModelAndView modelAndView, @PathVariable Integer articleId, @PathVariable String editorMode, HttpSession session) {
        modelAndView.addObject("article", articleService.getArticleById(articleId));
        modelAndView.addObject("editorMode", editorMode);
        if ("change".equals(editorMode)) {
            modelAndView.setViewName("/back/changeEditor");
        } else if ("new".equals(editorMode)) {
            modelAndView.setViewName("/back/newEditor");
        }
        return modelAndView;
    }

    /**
     * 删除文章
     */
    @DeleteMapping("/article/{articleId}")
    @ResponseBody
    public ResultInfo deleteArticle(@PathVariable Integer articleId) {

        System.out.println("删除articleId:" + articleId);
        Integer integer = articleService.deleteArticleById(articleId);
        AssertUtil.isTrue(integer==0, "删除文章失败");
        commentService.deleteCommentByArticleId(articleId);
        //TODO:
        //以下这一行不能加，因为有的文章没有评论，所以会报错
//        AssertUtil.isTrue(i==0, "删除文章的评论失败");

        return new ResultInfo(200,"删除文章成功");
    }

    /**
     * 跳转到文章页面 可以查看文章详情
     */
    @GetMapping("/article/{articleId}")
    public ModelAndView showArticle(ModelAndView modelAndView, @PathVariable Integer articleId) {

        return articleService.showArticle(modelAndView, articleId);
    }


}

