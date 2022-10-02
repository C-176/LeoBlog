package com.chen.LeoBlog.service;

import com.chen.LeoBlog.dao.ChatDao;
import com.chen.LeoBlog.po.ChatRecord;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatDao chatDao;

    public int insertRecord(ChatRecord chatRecord){
        return chatDao.insertRecord(chatRecord);
    }
    public List<ChatRecord> getRecordsBySenderIdOrReceiverId(Integer senderId, Integer receiverId){
        List<ChatRecord> records = chatDao.getRecordsBySenderIdOrReceiverId(senderId, receiverId);
        Collections.reverse(records);
        return records;

    }
    public int updateLastTime(Integer userId1, Integer userId2, String lastTime){
        return chatDao.updateLastTime(userId1,userId2,lastTime);
    }
    public int insertChatConnection(Integer userId1, Integer userId2, String lastTime){
        return chatDao.insertChatConnection(userId1,userId2,lastTime);
    }
    public int getConnection(Integer userId1, Integer userId2){
        return chatDao.getConnection(userId1,userId2);
    }

    public int deleteRecordById(Integer id){
        return chatDao.deleteRecordById(id);
    }
    public int deleteRecord(Integer senderId, Integer receiverId){
        return chatDao.deleteRecord(senderId, receiverId);
    }

}
