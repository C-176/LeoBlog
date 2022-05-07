package com.chen.LeoBlog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
@MapperScan("com.chen.LeoBlog.dao")
public class Starter extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(Starter.class);

//        springApplication.setBannerMode(Banner.Mode.OFF); // banner图标关闭
//        SpringApplication.run(Starter.class);

        springApplication.run();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Starter.class);
    }
}
