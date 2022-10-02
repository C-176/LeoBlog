package com.chen.LeoBlog.utils.net;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Objects;

import static com.chen.LeoBlog.utils.net.SocketPool.sessionMap;

public class SocketHandler {

    private static final Logger log = LoggerFactory.getLogger(SocketHandler.class);

    public static Integer createKey(Integer userId){
        return userId;
    }

    /**
     * 给指定用户发送信息
     * @param session session
     * @param msg 发送的消息
     */
    public static void sendMessage(Session session, String msg) {
        if (session == null)
            return;
        final RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null)
            return;
        try {
            basic.sendText(msg);
        } catch (IOException e) {
            log.error("消息发送异常，异常情况: {}", e.getMessage());
        }
    }

    public static void sendMessageAll(String message, Integer userId) {
        log.info("广播：群发消息");
        // 遍历map，只输出给其他客户端，不给自己重复输出
        sessionMap().forEach((keyId, session) -> {
            if (!Objects.equals(userId, keyId)) {
                sendMessage(session, message);
            }
        });
    }
}