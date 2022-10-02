package com.chen.LeoBlog.utils;

import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.exception.ParamsException;

public class AssertUtil {

    // 判断是否为真，为真的话抛出参数异常msg。

    public static ResultInfo isTrue(Boolean flag, String msg) {
        if (flag) return new ResultInfo(300, msg);
        return new ResultInfo(200,"操作成功");
    }

}
