package com.chen.LeoBlog.utils;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Tony on 2016/8/23.
 */
public class LoginUserUtil {

    /**
     * 从cookie中获取userId
     */
    public static int releaseUserIdFromCookie(HttpServletRequest request) {
        String userIdString = CookieUtil.getCookieValue(request, "userIdStr");
        if (StrUtil.isBlank(userIdString)) {
            return -1;
        }
        Integer userId = UserIDBase64.decoderUserID(userIdString);
        return userId;
    }
}
