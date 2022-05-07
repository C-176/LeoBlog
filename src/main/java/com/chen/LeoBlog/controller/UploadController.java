package com.chen.LeoBlog.controller;


import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class UploadController {
    @Autowired
    private UploadService uploadService;




    /**
     * ajax后台图片上传，返回图片路径。
     * @param uploadFile
     * @param request
     * @return
     */
    @PostMapping("/articlePicUpload")
    @ResponseBody
    public String articlePicUpload(@RequestParam("picUrl") MultipartFile uploadFile, HttpServletRequest request) {
        System.out.println("进入articlePicUpload");
        return uploadService.articlePicUpload(uploadFile, request);
    }

    /**
     * 图片上传，返回图片地址信息。
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/imageUpload")
    @ResponseBody
    public Map<String, String> uploadImage(MultipartFile file, HttpServletRequest request) {
        return uploadService.uploadImage(file, request);
    }

    /**
     * 文档上传功能，对于修改而言，传入修改过的项，没有修改过的项为null；对于新建，传入所有参数。
     * @param articleId
     * @param content
     * @param submitMode
     * @param editorMode
     * @param picUrl
     * @param original
     * @param title
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/contentUpload")
    @ResponseBody
    public ResultInfo uploadContent(Integer articleId, String content, String submitMode, String editorMode, String picUrl, int original, String title, HttpServletRequest request, HttpServletResponse response) {
        return uploadService.uploadContent(articleId, content, submitMode, editorMode, picUrl, original, title, request, response);
    }


}
