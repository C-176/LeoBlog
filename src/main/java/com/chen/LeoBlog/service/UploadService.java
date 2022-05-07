package com.chen.LeoBlog.service;


import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.po.Article;
import com.chen.LeoBlog.po.Script;
import com.chen.LeoBlog.po.User;
import com.chen.LeoBlog.utils.AssertUtil;
import com.chen.LeoBlog.utils.PathUtil;
import com.chen.LeoBlog.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * @author 1
 */
@Service
public class UploadService {
    private static final String IMAGE_URL_PREFIX = "http://localhost:8080/LeoBlog/source/upload/images/";
    //    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);
    @Autowired
    private ScriptService scriptService;
    @Autowired
    private ArticleService articleService;


    /**
     * 上传文件至指定目录
     *
     * @param file
     * @param path
     * @return 上传后的文件名
     */
    public String uploadFile(MultipartFile file, String path) {
        //设置上传文件大小上限为2M
        AssertUtil.isTrue(file.getSize() > 2097152, "上传文件大小不能超过2M");

        String filename = file.getOriginalFilename();
        // 获取文件扩展名
        assert filename != null;
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);

        // 设置上传文件的文件名，防止命名冲突导致覆盖
        String uploadFilename = UUID.randomUUID() + "." + extensionName;
        //TODO:实现图片与文章或者草稿绑定，使删除文章时图片一并删掉，当然要考虑：文章与草稿相互转换的情况。

        File uploadDir = new File(path);
        // 检查上传路径是否存在，不存在则创建
        if (!uploadDir.exists()) {
            // 设置可读权限，因为启用 tomcat 的用户可能没有写文件的权限
            uploadDir.setWritable(true);
            uploadDir.mkdirs();
        }

        File targetFile = new File(path, uploadFilename);

        try {


            file.transferTo(targetFile);
        } catch (IOException e) {
            AssertUtil.isTrue(true, "文章中图片上传失败");
            return null;
        }
        return "source/upload/images/" + targetFile.getName();
    }

    /**
     * 文章中的图片上传
     *
     * @param file
     * @param request
     * @return location：图片在服务器上的存储路径
     */
    public Map<String, String> uploadImage(MultipartFile file, HttpServletRequest request) {
        String realPath = PathUtil.getUploadPath(request) + "images";
        //        System.out.println(realPath);

//        将文件上传至指定目录
        String imageUrl = uploadFile(file, realPath);
        imageUrl = IMAGE_URL_PREFIX + imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
//        System.out.println(imageUrl);
        //  TinyMCE 要求图片上传后，需要返回一个 json 对象，这个对象必须有 location 属性，此处硬编码
        Map<String, String> map = new HashMap<>();
        map.put("location", imageUrl);
        System.out.println("loca:" + map.get("location"));
        return map;

    }

    /**
     * 发布文章，无论新建还是修改后发布，都会调用此方法
     *
     * @param editorMode
     * @param title
     * @param content
     * @param original
     * @param picUrl
     * @param articleId
     * @param request
     * @return
     */
    public ResultInfo articlePublish(String editorMode, String title, String content, Integer original, String picUrl, Integer articleId, HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        int i = 0;
        if (!picUrl.contains("/") && StringUtils.isNotBlank(picUrl)) {
            picUrl = "source/upload/images/" + picUrl;
        }
        if ("new".equals(editorMode)) {
            //先从session中得到当前用户对象
            User user = (User) request.getSession().getAttribute("user");
            AssertUtil.isTrue(user == null, "请先登录...");

            Article article = new Article();
            article.setArticleId(Util.getId());
            article.setTitle(title);
            picUrl = StringUtils.isBlank(picUrl) ? "https://pic2.zhimg.com/80/v2-29c63c3b7adb27988d6b4b808eb4b59d_400x224.png" : picUrl;

            article.setPicUrl(picUrl);
            article.setUserId(user.getUserId());
            article.setAuthor(user.getUserName());
            article.setChangedTime(new Date());
            article.setComment(content);
            article.setOriginal(original);
            System.out.println(article);
            i = articleService.insertArticle(article);

        } else if (Objects.equals(editorMode, "change")) {
            Article article = new Article();
            if (articleService.getArticleById(articleId) != null) {

                article = articleService.getArticleById(articleId);
                if (StringUtils.isNotBlank(title)) {
                    article.setTitle(title);
                }
                if (StringUtils.isNotBlank(content)) {
                    article.setComment(content);
                }
                if (StringUtils.isNotBlank(picUrl)) {
                    article.setPicUrl(picUrl);
                }
                if (original != null) {
                    article.setOriginal(original);
                }
                article.setChangedTime(new Date());
                i = articleService.changeArticle(article);


            } else {
                Script script = scriptService.getScriptById(articleId);

                AssertUtil.isTrue(script == null, "文章不存在,请新建文章...");
                assert script != null;
                //先从session中得到当前用户对象
                User user = (User) request.getSession().getAttribute("user");
                AssertUtil.isTrue(user == null, "请先登录...");
                article.setArticleId(Util.getId());
                title = StringUtils.isBlank(title) ? script.getTitle() : title;
                article.setTitle(title);
                picUrl = StringUtils.isBlank(picUrl) ? script.getPicUrl() : picUrl;
                picUrl = StringUtils.isBlank(picUrl) ? "https://pic2.zhimg.com/80/v2-29c63c3b7adb27988d6b4b808eb4b59d_400x224.png" : picUrl;
                article.setPicUrl(picUrl);
                article.setUserId(user.getUserId());
                article.setAuthor(user.getUserName());
                article.setChangedTime(new Date());
                content = StringUtils.isBlank(content) ? script.getComment() : content;
                article.setComment(content);
                original = original == null ? script.getOriginal() : original;
                article.setOriginal(original);
                i = articleService.insertArticle(article);
                scriptService.deleteScriptById(articleId);
            }
        }
        AssertUtil.isTrue(i < 1, "文章发布失败,请重试");
        resultInfo.setCode(200);
        resultInfo.setMsg("文章发布成功");

        return resultInfo;
    }

    /**
     * 保存草稿，无论新建还是修改都会调用
     *
     * @param editorMode
     * @param title
     * @param content
     * @param original
     * @param picUrl
     * @param articleId
     * @param request
     * @return
     */
    public ResultInfo scriptPublish(String editorMode, String title, String content, Integer original, String picUrl, Integer articleId, HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        int i = 0;
        if (!picUrl.contains("/") && StringUtils.isNotBlank(picUrl)) {
            picUrl = "source/upload/images/" + picUrl;
        }

        if (Objects.equals(editorMode, "new")) {
            //先从session中得到当前用户对象
            User user = (User) request.getSession().getAttribute("user");
            AssertUtil.isTrue(user == null, "请先登录...");
            Script script = new Script();
            script.setScriptId(Util.getId());
            script.setUserId(user.getUserId());
            script.setChangedTime(new Date());
            picUrl = StringUtils.isBlank(picUrl) ? "https://pic2.zhimg.com/80/v2-29c63c3b7adb27988d6b4b808eb4b59d_400x224.png" : picUrl;
            script.setPicUrl(picUrl);
            System.out.println(script.getPicUrl());
            script.setTitle(title);
            script.setComment(content);
            script.setAuthor(user.getUserName());
            script.setOriginal(original);

            i = scriptService.insertScript(script);
        } else if (Objects.equals(editorMode, "change")) {
            Script script = new Script();
            if (scriptService.getScriptById(articleId) != null) {
                script = scriptService.getScriptById(articleId);
                if (StringUtils.isNotBlank(title)) {
                    script.setTitle(title);
                }
                if (StringUtils.isNotBlank(content)) {
                    script.setComment(content);
                }
                if (StringUtils.isNotBlank(picUrl)) {
                    script.setPicUrl(picUrl);
                }
                if (original != null) {
                    script.setOriginal(original);
                }
                script.setChangedTime(new Date());
                System.out.println("-----------------" + script);
                i = scriptService.changeScript(script);
                System.out.println("-----------------" + i);
            } else {
                Article articleById = articleService.getArticleById(articleId);
                User user = (User) request.getSession().getAttribute("user");
                script.setScriptId(Util.getId());
                script.setUserId(user.getUserId());
                script.setChangedTime(new Date());
                picUrl = StringUtils.isBlank(picUrl) ? articleById.getPicUrl() : picUrl;
                script.setPicUrl(picUrl);
                title = StringUtils.isBlank(title) ? articleById.getTitle() : title;
                script.setTitle(title);
                content = StringUtils.isBlank(content) ? articleById.getComment() : content;
                script.setComment(content);
                original = original == null ? articleById.getOriginal() : original;
                script.setOriginal(original);
                script.setAuthor(articleById.getAuthor());
                i = scriptService.insertScript(script);
                articleService.deleteArticleById(articleId);


            }
        }
        AssertUtil.isTrue(i < 1, "草稿保存失败,请重试");

        resultInfo.setCode(200);
        resultInfo.setMsg("草稿保存成功");
        return resultInfo;
    }


    public String articlePicUpload(MultipartFile uploadFile, HttpServletRequest request) {
        String realPath = PathUtil.getUploadPath(request) + "images";
        return uploadFile(uploadFile, realPath);

    }

    public ResultInfo uploadContent(Integer articleId, String content, String submitMode, String editorMode, String picUrl, int original, String title, HttpServletRequest request, HttpServletResponse response) {
        switch (submitMode) {
            case "asArticle":
                return articlePublish(editorMode, title, content, original, picUrl, articleId, request);
            case "asScript":
                return scriptPublish(editorMode, title, content, original, picUrl, articleId, request);
            default:
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("参数错误");
                return resultInfo;
        }
    }
}
