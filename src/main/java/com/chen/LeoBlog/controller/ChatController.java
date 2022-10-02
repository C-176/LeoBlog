package com.chen.LeoBlog.controller;



import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.chen.LeoBlog.base.ResultInfo;
import com.chen.LeoBlog.po.ChatRecord;
import com.chen.LeoBlog.po.User;
import com.chen.LeoBlog.service.ChatService;
import com.chen.LeoBlog.service.UserService;
import com.chen.LeoBlog.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Controller
public class ChatController {

    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @RequestMapping("/chat/{userId}")
    public ModelAndView chat(@PathVariable Integer userId, ModelAndView modelAndView, HttpSession session) {
        User user = (User) session.getAttribute("user");
        AssertUtil.isTrue(user == null,"未登录");

//        modelAndView.addObject("username", userId);
        modelAndView.setViewName("back/chat");
        return modelAndView;
    }
    @GetMapping("/chat/record/{receiverId}")
    @ResponseBody
    public String getRecords(@PathVariable Integer receiverId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer senderId = user.getUserId();
        AssertUtil.isTrue(user == null,"未登录");
        List<ChatRecord> records = chatService.getRecordsBySenderIdOrReceiverId(senderId, receiverId);
        return JSONUtil.toJsonStr(records);
    }

//    @PostMapping("/chat/record/{receiverId}")
//    @ResponseBody
    public ResultInfo insertRecord(ChatRecord chatRecord) {

        int i = chatService.insertRecord(chatRecord);
        if(i == 1)  return new ResultInfo(200,"发送成功");
        else return null;
    }

    @DeleteMapping("/chat/record/{id}")
    @ResponseBody
    public ResultInfo deleteRecordById(@PathVariable Integer id){
        int i = chatService.deleteRecordById(id);
        AssertUtil.isTrue(i==0,"此条记录删除失败");
        return new ResultInfo(200, "此条记录删除成功");
    }

}
