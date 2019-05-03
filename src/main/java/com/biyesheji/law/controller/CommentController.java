package com.biyesheji.law.controller;

import com.biyesheji.law.pojo.Comment;
import com.biyesheji.law.pojo.Question;
import com.biyesheji.law.service.CommentService;
import com.biyesheji.law.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final QuestionService questionService;

    public CommentController(CommentService commentService, QuestionService questionService) {
        this.commentService = commentService;
        this.questionService = questionService;
    }

    @GetMapping("/addComment")
    public String addComment(@RequestParam int questionId,Model model){
        List<Comment> commentList = commentService.findComment(questionId);
        Question question = questionService.findOneQuestion(questionId);
        question.setCommentList(commentList);
        model.addAttribute("question",question);

        return "addComment";
    }
    @PostMapping("/commentQuestion")
    public String commentQuestion(String comment, String questionId,HttpServletRequest request){
        int userId = (int) request.getSession().getAttribute("userId");
        int question_Id = Integer.parseInt(questionId);
        Comment new_comment = new Comment();

        new_comment.setContent(comment);
        new_comment.setUserId(userId);
        new_comment.setQuestionId(question_Id);

        commentService.addComment(new_comment);
        return "redirect:addComment"+"?questionId="+questionId;
    }
}
