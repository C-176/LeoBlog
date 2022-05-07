package com.chen.LeoBlog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller

public class Controller {
    @RequestMapping("/getUse")
    @ResponseBody
    public String get(int user) {
        System.out.println("getUser");
        return "getUser";
    }
}
