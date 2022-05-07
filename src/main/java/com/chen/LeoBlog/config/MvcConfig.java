package com.chen.LeoBlog.config;


import com.chen.LeoBlog.interceptors.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 配置拦截器对象
     *
     * @return
     */
    @Bean
    public NoLoginInterceptor noLoginInterceptor() {
        return new NoLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        被拦截的页面都跳转到登录页面

        /*指定拦截器对象*/
        registry.addInterceptor(noLoginInterceptor())
                /*拦截路径*/
                .addPathPatterns("/**")
                /*放行路径*/
                .excludePathPatterns("/index.html", "/LR.html", "/ToIndex", "/login", "/register", "/js/**", "/css/**", "/source/**");
    }
}
