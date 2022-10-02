package com.chen.LeoBlog.config;

import com.chen.LeoBlog.interceptors.NoLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // 静态资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("静态资源文映射配置文件生效...");
        //关于图片上传后需要重启服务器才能刷新图片
        //这是一种保护机制，为了防止绝对路径被看出来，目录结构暴露
        //解决方法:将虚拟路径/source/upload/images/
        //        向绝对路径 (D:\\Javacode\\LeoBlog\\src\\main\\resources\\static\\source\\upload\\images\\)映射

        String path = "D:\\Javacode\\LeoBlog\\src\\main\\resources\\static\\source\\upload\\images\\";
        registry.addResourceHandler("/source/upload/images/**").addResourceLocations("file:" + path);

    }
    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new NoLoginInterceptor()).addPathPatterns("/x/**")
                .excludePathPatterns("/index.html","/sendEmail/*", "/LR.html", "/ToIndex","/security/**/find", "/login", "/register", "/js/**", "/css/**", "/source/**","/getCaptcha");

    }

//    CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")   // 允许跨域访问的路径
                .allowedOriginPatterns("*")  // 允许跨域访问的源
                .allowedMethods("POST","GET","PUT","OPTIONS","DELETE")  // 允许请求方法
                .maxAge(168000)  // 预检间隔时间
                .allowedHeaders("*")  // 允许头部设置
                .allowCredentials(true); // 是否允许发送cookie
    }
}

