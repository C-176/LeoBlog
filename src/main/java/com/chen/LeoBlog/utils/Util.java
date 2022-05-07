package com.chen.LeoBlog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    /**
     * 获取id，从时间戳截取得到的id
     *
     * @return
     */
    public static int getId() {
        Date date = new Date();
        long time = date.getTime();
        return Math.toIntExact((time / 1000) % 1000000000);
    }

    /**
     * 格式化时间，返回字符串。
     *
     * @param date
     * @return
     */
    public static String timeFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
