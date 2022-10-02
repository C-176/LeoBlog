package com.chen.LeoBlog.dao;

import com.chen.LeoBlog.po.EmailConfirm;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface EmailDao {

    @Insert("insert into leoblog.emailconfirm values (#{id},#{userId},#{userEmail},#{confirmCode},#{confirmDate},#{confirmTime})")
    int sendConfirmCode(EmailConfirm emailConfirm);

    @Update("update leoblog.emailconfirm set confirmCode = #{confirmCode},confirmDate=#{confirmDate},confirmTime=#{confirmTime} where userEmail=#{userEmail} limit 1")
    int updateCode(EmailConfirm emailConfirm);

    @Select("select confirmCode from leoblog.emailconfirm where userEmail=#{userEmail} limit 1")
    int getConfirmCode(String userEmail);

    @Select("select count(confirmCode) from leoblog.emailconfirm where userEmail=#{userEmail}")
    int getConfirmCodeLen(String userEmail);

    @Delete("delete from leoblog.emailconfirm where userEmail=#{userEmail} limit 1")
    int deleteCode(EmailConfirm emailConfirm);
}
