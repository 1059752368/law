package com.biyesheji.law.repository;

import com.biyesheji.law.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer>{

    List<Comment> findCommentByQuestionId(int questionId);

}
