package com.biyesheji.law.service;


import com.biyesheji.law.pojo.Answers;
import com.biyesheji.law.pojo.Question;

import java.util.List;

public interface QuestionService {
    String geiAnalysis(int questionId);
    Question findOneQuestion(int questionId);
    List<Question> randomTen();
    List<Question> getAll();
    List<Question> getAllQuestionAndComment();
    Answers getAnswerByAnswerId(int answerId);
    void addQuestion(Question question, Answers answer);//增加问题以及答案
    void delete(int questionId);//删除
    void updateQuestion(Question question);//修改
}
