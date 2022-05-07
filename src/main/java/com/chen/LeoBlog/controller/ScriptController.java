package com.chen.LeoBlog.controller;

import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.service.ArticleService;
import com.chen.LeoBlog.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ScriptController {
    @Autowired
    private ScriptService scriptService;
    @Autowired
    private ArticleService articleService;


    /**
     * 查看用户所有的草稿
     *
     * @param modelAndView
     * @param userId
     * @param session
     * @return
     */
    @GetMapping("/scripts/mine/{userId}")
    public ModelAndView showMyScripts(ModelAndView modelAndView, @PathVariable Integer userId, HttpSession session) {
        return scriptService.showMyScripts(modelAndView, userId, session);
    }

    /**
     * 跳转到草稿页面，查看草稿详情
     *
     * @param modelAndView
     * @param scriptId
     * @return
     */
    @GetMapping("/script/{scriptId}")
    public ModelAndView showArticle(ModelAndView modelAndView, @PathVariable Integer scriptId, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        return articleService.showArticle(modelAndView, scriptId, session, request, response);
    }

    /**
     * 删除草稿
     *
     * @param scriptId
     * @param request
     * @return
     */
    @DeleteMapping("/script/{scriptId}")
    @ResponseBody
    public ResultInfo deleteScript(@PathVariable Integer scriptId, HttpServletRequest request) {
        return scriptService.deleteScript(scriptId, request);
    }

    /**
     * 将草稿转化为文章，提交。
     *
     * @param scriptId
     * @param session
     * @return
     */
    @PostMapping("/script/submit/{scriptId}")
    @ResponseBody
    public ResultInfo articlePublish(@PathVariable Integer scriptId, HttpSession session) {
        return scriptService.articlePublish(scriptId, session);
    }

    /**
     * 更改或新新建草稿
     *
     * @param modelAndView
     * @param scriptId
     * @param editorMode
     * @param session
     * @return
     */
    @GetMapping("/script/{scriptId}/{editorMode}")
    public ModelAndView editor(ModelAndView modelAndView, @PathVariable Integer scriptId, @PathVariable String editorMode, HttpSession session) {
        return scriptService.editor(modelAndView, scriptId, editorMode, session);
    }
}
