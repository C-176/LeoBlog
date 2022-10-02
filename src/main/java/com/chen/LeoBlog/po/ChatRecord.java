package com.chen.LeoBlog.po;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ChatRecord {
    public Integer id;
    public Integer senderId;
    public Integer receiverId;
    public String content;
    public String create_datetime;

    public ChatRecord() {
    }

    public ChatRecord(Integer senderId, Integer receiverId, String content, String create_datetime) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.create_datetime = create_datetime;
    }
}
