package com.chen.LeoBlog.dao;

import com.chen.LeoBlog.po.ChatRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChatDao {
    @Insert("insert into leoblog.chat_record (senderId,receiverId,content,create_datetime) values (#{senderId},#{receiverId},#{content},#{create_datetime})")
    int insertRecord(ChatRecord chatRecord);
    @Select("select * from leoblog.chat_record where (senderId=#{senderId} and receiverId=#{receiverId}) or (receiverId=#{senderId} and senderId=#{receiverId}) order by create_datetime desc limit 50")
    List<ChatRecord> getRecordsBySenderIdOrReceiverId(@Param("senderId") Integer senderId,@Param("receiverId") Integer receiverId);
    @Update("update leoblog.chat_connection set last_time = #{last_time} where (userId1=#{userId1} and userId2=#{userId2}) or (userId1=#{userId2} and userId2=#{userId1}) limit 1")
    int updateLastTime(@Param("userId1")Integer userId1,@Param("userId2")Integer userId2,@Param("last_time")String lastTime);
    @Insert("insert into leoblog.chat_connection( userId1, userId2, last_time) values (#{userId1},#{userId2},#{last_time})")
    int insertChatConnection(@Param("userId1")Integer userId1,@Param("userId2")Integer userId2,@Param("last_time")String lastTime);
    @Select("select count(*) from leoblog.chat_connection where (userId1=#{userId1} and userId2=#{userId2}) or (userId1=#{userId2} and userId2=#{userId1})")
    int getConnection(@Param("userId1")Integer userId1,@Param("userId2")Integer userId2);
    @Delete("delete from leoblog.chat_record where id=#{id} limit 1")
    int deleteRecordById(Integer id);
    @Delete("delete from leoblog.chat_record where (senderId=#{senderId} and receiverId=#{receiverId}) or (receiverId=#{senderId} and senderId=#{receiverId}) order by create_datetime desc limit 50")
    int deleteRecord(@Param("senderId") Integer senderId,@Param("receiverId") Integer receiverId);
}
