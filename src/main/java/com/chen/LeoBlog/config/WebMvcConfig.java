package com.chen.LeoBlog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("静态资源文映射配置文件生效...");
        //关于图片上传后需要重启服务器才能刷新图片
        //这是一种保护机制，为了防止绝对路径被看出来，目录结构暴露
        //解决方法:将虚拟路径/source/upload/images/
        //        向绝对路径 (D:\\Javacode\\springboot\\src\\main\\resources\\static\\source\\upload\\images\\)映射

        String path = "D:\\Javacode\\springboot\\src\\main\\resources\\static\\source\\upload\\images\\";
        registry.addResourceHandler("/source/upload/images/**").addResourceLocations("file:" + path);


    }
}

