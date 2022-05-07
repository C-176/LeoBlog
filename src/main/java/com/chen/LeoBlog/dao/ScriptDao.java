package com.chen.LeoBlog.dao;

import com.chen.LeoBlog.po.Script;

import java.util.ArrayList;

public interface ScriptDao {
    Script getScriptById(Integer scriptId);

    ArrayList<Script> getScriptByUserId(Integer userId);

    Integer deleteScriptById(Integer scriptId);

    int insertScript(Script script);

    int changeScript(Script script);
}
