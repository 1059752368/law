package com.biyesheji.law.service.impl;

import com.biyesheji.law.pojo.Comment;
import com.biyesheji.law.repository.CommentRepository;
import com.biyesheji.law.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(int CommentId) {
        commentRepository.deleteById(CommentId);
    }

    @Override
    public List<Comment> findComment(int QuestionID) {
        return commentRepository.findCommentByQuestionId(QuestionID);
    }
}
