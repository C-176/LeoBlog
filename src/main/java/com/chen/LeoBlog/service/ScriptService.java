package com.chen.LeoBlog.service;

import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.dao.ScriptDao;
import com.chen.LeoBlog.po.Article;
import com.chen.LeoBlog.po.Script;
import com.chen.LeoBlog.po.User;
import com.chen.LeoBlog.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;

@Service
public class ScriptService {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ScriptDao scriptDao;

    public Script getScriptById(Integer scriptId) {
        return scriptDao.getScriptById(scriptId);
    }

    public ArrayList<Script> getScriptByUserId(Integer userId) {
        return scriptDao.getScriptByUserId(userId);
    }

    public Integer deleteScriptById(Integer scriptId) {
        return scriptDao.deleteScriptById(scriptId);
    }

    public int insertScript(Script script) {
        return scriptDao.insertScript(script);
    }

    public int changeScript(Script script) {
        return scriptDao.changeScript(script);
    }

    public ModelAndView showMyScripts(ModelAndView modelAndView, Integer userId, HttpSession session) {
        ArrayList<Script> scripts = scriptDao.getScriptByUserId(userId);
        modelAndView.addObject("myScripts", scripts);
        modelAndView.setViewName("back/script");
        return modelAndView;
    }

    public ResultInfo deleteScript(Integer scriptId, HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        Script script = getScriptById(scriptId);
        AssertUtil.isTrue(script == null, "该草稿不存在");
        Integer integer = deleteScriptById(script.getScriptId());
        AssertUtil.isTrue(integer < 1, "删除草稿失败");
        resultInfo.setCode(200);
        resultInfo.setMsg("删除草稿成功");
        return resultInfo;
    }


    public ModelAndView editor(ModelAndView modelAndView, Integer scriptId, String editorMode, HttpSession session) {
        Script script = getScriptById(scriptId);
        Article article = new Article();
        article.setComment(script.getComment());
        article.setTitle(script.getTitle());
        article.setUserId(script.getUserId());
        article.setChangedTime(script.getChangedTime());
        article.setOriginal(script.getOriginal());
        article.setAuthor(script.getAuthor());
        article.setPicUrl(script.getPicUrl());
        article.setArticleId(script.getScriptId());
        modelAndView.addObject("article", article);
        modelAndView.addObject("editorMode", editorMode);
        if ("change".equals(editorMode)) {
            modelAndView.setViewName("back/changeEditor");
        } else if ("new".equals(editorMode)) {
            modelAndView.setViewName("back/newEditor");
        }
        return modelAndView;
    }

    public ResultInfo articlePublish(Integer scriptId, HttpSession session) {
        ResultInfo resultInfo = new ResultInfo();
        Script script = getScriptById(scriptId);
        Article article = new Article();

        User user = (User) session.getAttribute("user");
        article.setAuthor(user.getUserName());
        article.setChangedTime(new Date());
        article.setPicUrl(script.getPicUrl());
        article.setUserId(user.getUserId());
        article.setComment(script.getComment());
        article.setTitle(script.getTitle());
        article.setArticleId(scriptId);
        article.setOriginal(script.getOriginal());
        int i = articleService.insertArticle(article);
        AssertUtil.isTrue(i == 0, "发布失败");
        Integer integer = deleteScriptById(scriptId);
        AssertUtil.isTrue(integer == 0, "删除原草稿失败");
        resultInfo.setCode(200);
        resultInfo.setMsg("发布成功");
        return resultInfo;
    }
}
