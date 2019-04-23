package com.biyesheji.law.service;

import com.biyesheji.law.pojo.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    void deleteComment(int CommentId);
    List<Comment> findComment(int QuestionID);
}