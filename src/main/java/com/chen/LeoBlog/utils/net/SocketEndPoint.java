package com.chen.LeoBlog.utils.net;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.controller.ChatController;
import com.chen.LeoBlog.po.ChatRecord;
import com.chen.LeoBlog.po.User;
import com.chen.LeoBlog.service.ChatService;
import com.chen.LeoBlog.service.UserService;
//import com.chen.LeoBlog.service.UserService;
import com.chen.LeoBlog.utils.AssertUtil;
import com.chen.LeoBlog.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.chen.LeoBlog.utils.net.SocketHandler.sendMessage;
import static com.chen.LeoBlog.utils.net.SocketPool.*;
import static com.chen.LeoBlog.utils.net.SocketHandler.createKey;

// 注入容器
@Component
//@Controller
// 表明这是一个websocket服务的端点
@ServerEndpoint("/net/websocket/{userId}")
public class SocketEndPoint {
    public static SocketEndPoint socketEndpoint; //public极为重要

    private static final Logger log = LoggerFactory.getLogger(SocketEndPoint.class);
//    @Autowired
//    private static UserService userService;
//
//    @Autowired
//    public void setUserService(UserService userService){
//        SocketEndPoint.userService = userService;
//    }
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        socketEndpoint = this;
        socketEndpoint.chatService = this.chatService ;
        socketEndpoint.userService = this.userService ;
        // 初使化时将已静态化的otherService实例化
    }



    @OnOpen
    public void onOpen(@PathParam("userId") Integer userId,  Session session) {
        log.info("新连接：{}", session);
        add(createKey(userId), session);
        for (Map.Entry<Integer, Session> item : sessionMap().entrySet()) {
            if (item.getKey()==userId) {
//                System.out.println(item.getKey()+" "+userId);
                SocketHandler.sendMessageAll("notice|0| " + socketEndpoint.userService.getUserById(userId).getUserName() + "已连接", userId);
            }
        }
        log.info("在线人数：{}",count());
        sessionMap().keySet().forEach(item -> log.info("在线用户：" + item));
    }

    @OnMessage
    public void onMessage(String message) {

        if (message.contains("[allUsers]")) {
            System.out.println("广播");
//            String userInfo = message.substring(message.indexOf("[allUsers]")).replace("[allUsers]----------", "");
//            SocketHandler.sendMessageAll( "<div style='width: 100%; float: left;'>&nbsp;&nbsp;" + userInfo + "群发消息</div><div style='width: 100%; font-size: 18px; font-weight: bolder; float: right;'>" + message.substring(0, message.indexOf("[")) + "</div>", userInfo);
        } else {
            JSONObject jsonObject = JSONUtil.parseObj(message);
            Integer acceptUser = jsonObject.getInt("acceptUser");

            Integer sendUser = jsonObject.getInt("sendUser");
            String msg = jsonObject.getStr("msg");
            Integer id  = jsonObject.getInt("id");

            Session userSession;

            for (Map.Entry<Integer, Session> item : sessionMap().entrySet()) {

                if (Objects.equals(item.getKey(), acceptUser)) {
                    userSession = item.getValue();
                    try {
                        SocketHandler.sendMessage(userSession, "msg|"+sendUser+"|'<div id='yourMessage' data='"+id+"''>"+msg+"</div>");
                    }catch (Exception e) {
                        sendMessage(sessionMap().get(sendUser),"notice|0|消息发送失败");
                        System.out.println(e.getMessage());
                    }

                }
            }
            String now = DateUtil.now();
            ChatRecord record = new ChatRecord(sendUser, acceptUser, msg, now);

            int i = socketEndpoint.chatService.insertRecord(record);
            int connection = socketEndpoint.chatService.getConnection(sendUser, acceptUser);
            if(connection == 0) socketEndpoint.chatService.insertChatConnection(sendUser,acceptUser,DateUtil.now());
            else {
                socketEndpoint.chatService.updateLastTime(sendUser,acceptUser,DateUtil.now());
            }
            AssertUtil.isTrue(i==0,"消息保存失败");

        }
        log.info("有新消息： {}", message);
    }

    @OnClose
    public void onClose(@PathParam("userId") Integer userId,Session session) {
        log.info("连接关闭： {}", session);
        remove(createKey(userId));
        log.info("在线人数：{}", count());
        sessionMap().keySet().forEach(item -> log.info("在线用户：" + item));
        SocketHandler.sendMessageAll("notice|0| " + socketEndpoint.userService.getUserById(userId).getUserName() + "断开连接",userId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            log.error("退出发生异常： {}", e.getMessage());
        }
        log.info("连接出现异常： {}", throwable.getMessage());
    }
}