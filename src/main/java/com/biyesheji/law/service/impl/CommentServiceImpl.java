package com.biyesheji.law.service.impl;

import com.biyesheji.law.pojo.Comment;
import com.biyesheji.law.pojo.User;
import com.biyesheji.law.repository.CommentRepository;
import com.biyesheji.law.repository.UserRepository;
import com.biyesheji.law.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
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
        List<Comment> commentList = commentRepository.findCommentByQuestionId(QuestionID);
        for (Comment comment:commentList
             ) {
            User user = userRepository.findById(comment.getUserId()).orElse(null);
            comment.setUser(user);
        }
        return commentList;
    }
}
