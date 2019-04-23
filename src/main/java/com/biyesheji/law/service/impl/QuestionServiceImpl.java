package com.biyesheji.law.service.impl;

import com.biyesheji.law.pojo.Answers;
import com.biyesheji.law.pojo.Question;
import com.biyesheji.law.repository.AnswersRepository;
import com.biyesheji.law.repository.QuestionRepository;
import com.biyesheji.law.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final AnswersRepository answersRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswersRepository answersRepository) {
        this.questionRepository = questionRepository;
        this.answersRepository = answersRepository;
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
        Answers answers = answersRepository.findById(question.getId()).orElse(null);
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
//        List<Answers> answersList = ;
//        List<Integer> collect = list.stream()
//                .map(Question::getAnswersId)
//                .collect(Collectors.toList());
//        collect.stream()
//                .collect(e -> {
//                   return  answersRepository.findById(e);
//                })
        for (Question question : list){
            Answers answers = answersRepository.findById(question.getAnswersId()).orElse(null);
            question.setAnswers(answers);
        }
        return list;
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
    public void updateQuestion(Question question) {
//        int questionId = question.getId();
//        int answerId = question.getAnswersId();
//        questionRepository.save(question);
        questionRepository.save(question);
        answersRepository.save(question.getAnswers());
    }

}
