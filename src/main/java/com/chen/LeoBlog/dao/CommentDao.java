package com.chen.LeoBlog.dao;

import com.chen.LeoBlog.po.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;

public interface CommentDao {

    Comment getCommentById(int commentId);

    ArrayList<Comment> getCommentsBySenderId(int senderId);

    ArrayList<Comment> getCommentsByReceiverId(int receiverId);

    ArrayList<Comment> getOneLevelCommentsByArticleId(int articleId);

    ArrayList<Comment> getOneLevelCommentsByArticleId0(int articleId);

    ArrayList<Comment> getCommentsByToId(int commentToId);

    ArrayList<Comment> getTwoLevelCommentsBySenderIdAndReceiverId(@Param("senderId") int senderId, @Param("receiverId") int receiverId, @Param("articleId") int articleId);

    int insertComment(Comment comment);
    int changeComment(@Param("content") String content,@Param("commentId") int commentId,@Param("changedTime") Date changedTime);

    int deleteComment(int commentId);

    int deleteCommentByArticleId(int articleId);

    int deleteCommentBySenderId(int senderId);

    int deleteCommentByReceiverId(int receiverId);

}
