package com.chen.LeoBlog.dao;

import com.chen.LeoBlog.po.Script;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface ScriptDao {
    @Select("select * from leoblog.script where scriptId = #{scriptId}")
    Script getScriptById(Integer scriptId);

    @Select("select * from leoblog.script where userId = #{userId} order by changedTime desc")
    ArrayList<Script> getScriptByUserId(Integer userId);

    @Delete("DELETE FROM leoblog.script WHERE scriptId = #{scriptId} limit 1")
    Integer deleteScriptById(Integer scriptId);

    @Insert("INSERT INTO leoblog.script(scriptId, comment, changedTime, userId, title, picUrl, scriptUrl, original, author)\n" +
            "        VALUES (#{scriptId}, #{comment}, #{changedTime}, #{userId}, #{title}, #{picUrl}, #{scriptUrl}, #{original},\n" +
            "                #{author});")
    int insertScript(Script script);

    @Update("UPDATE leoblog.script\n" +
            "        SET changedTime = #{changedTime},\n" +
            "            comment     = #{comment},\n" +
            "            title=#{title},\n" +
            "            picUrl=#{picUrl},\n" +
            "            original=#{original}\n" +
            "        WHERE scriptId = #{scriptId}\n" +
            "        limit 1")
    int changeScript(Script script);
}
