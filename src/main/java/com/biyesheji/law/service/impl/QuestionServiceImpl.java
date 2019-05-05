package com.biyesheji.law.service.impl;

import com.biyesheji.law.pojo.Answers;
import com.biyesheji.law.pojo.Comment;
import com.biyesheji.law.pojo.Question;
import com.biyesheji.law.repository.AnswersRepository;
import com.biyesheji.law.repository.CommentRepository;
import com.biyesheji.law.repository.QuestionRepository;
import com.biyesheji.law.repository.UserRepository;
import com.biyesheji.law.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;
    private final AnswersRepository answersRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, CommentRepository commentRepository, AnswersRepository answersRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.commentRepository = commentRepository;
        this.answersRepository = answersRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String geiAnalysis(int questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        return question.getAnalysis();
    }

    @Override
    public Question findOneQuestion(int questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) {
            return null;
        }
        Answers answers = answersRepository.findById(question.getAnswersId()).orElse(null);
        question.setAnswers(answers);
        return question;
    }

    @Override
    public List<Question> randomTen() {
        List<Question> questionRandTen = questionRepository.findTenRandQues();
        for (Question question : questionRandTen){
            Answers answers = answersRepository.findById(question.getAnswersId()).orElse(null);
            question.setAnswers(answers);
        }
        return questionRandTen;
    }

    @Override
    public List<Question> getAll() {
        List<Question> list = questionRepository.findAll();
        for (Question question : list){
            Answers answers = answersRepository.findById(question.getAnswersId()).orElse(null);
            question.setAnswers(answers);
        }
        return list;
    }

    @Override
    public List<Question> getAllQuestionAndComment() {
        List<Question> questionList = questionRepository.findAll();
        for (Question question:questionList
             ) {
            List<Comment> comments = commentRepository.findCommentByQuestionId(question.getId());
            for (Comment comment:
                 comments) {
                comment.setUser(userRepository.findById(comment.getUserId()).orElse(null));
            }
            question.setCommentList(commentRepository.findCommentByQuestionId(question.getId()));
            question.setAnswers(answersRepository.findById(question.getAnswersId()).orElse(null));
        }
        return  questionList;
    }

    @Override
    public Answers getAnswerByAnswerId(int answerId) {
        Answers answers = answersRepository.findById(answerId).orElse(null);
        return answers;
    }

    @Override
    public void addQuestion(Question question ,Answers answer) {
        Answers savedAnswers = answersRepository.save(answer);
        question.setAnswersId(savedAnswers.getId());
        questionRepository.save(question);
    }

    @Override
    public void delete(int questionId) {
        int answerId = questionRepository.findById(questionId).orElse(null).getAnswersId();
        answersRepository.deleteById(answerId);
        questionRepository.deleteById(questionId);
    }

    @Override
    public void updateQuestion(Question question,Answers answers) {
//        int questionId = question.getId();
//        int answerId = question.getAnswersId();
//        questionRepository.save(question);
        questionRepository.save(question);
        answersRepository.save(answers);
    }

}
