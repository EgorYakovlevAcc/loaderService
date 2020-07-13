package com.service;

import com.model.Question;
import com.pojo.QuestionOptionsAnswer;

import java.util.List;

public interface QuestionService {
    Question findQuestionById(Integer id);
    List<Question> findAll();
    void save(Question question);
    void delete(Question question);
    Question getNextQuestion(Integer currentQuestionId);
    Question getNextQuestionByWeight(Integer weight);
    void deleteQuestionById(Integer id);
    void saveQuestionWithImageContent(Question question, byte[] image);
    List<Question> findAllQuestionsForPorters();
    List<Question> findAllQuestionsForCustomers();
}
