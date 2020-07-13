package com.service.impl;

import com.model.Question;
import com.pojo.Option;
import com.pojo.QuestionOptionsAnswer;
import com.repo.QuestionRepository;
import com.service.OptionService;
import com.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question findQuestionById(Integer id) {
        return questionRepository.findQuestionById(id);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    @Override
    public Question getNextQuestion(Integer currentQuestionId) {
        List<Question> questions = questionRepository.findAll();
        Integer currentQuestionIndex = questions.stream()
                .map(question -> question.getId())
                .collect(Collectors.toList())
                .indexOf(currentQuestionId);
        if ((currentQuestionIndex < questions.size() - 1) && (Objects.nonNull(currentQuestionId))) {
            Question question = questions.get(currentQuestionIndex + 1);
            return question;
        } else {
            return null;
        }
    }

    @Override
    public Question getNextQuestionByWeight(Integer weight) {
        return null;
    }

    @Override
    public void deleteQuestionById(Integer id) {
        questionRepository.deleteById(id);
    }

    @Override
    public void saveQuestionWithImageContent(Question question, byte[] image) {
        question.setAttachement(image);
        questionRepository.save(question);
    }

    @Override
    public Question getNextQuestionByNumber(Integer number) {
        List<Question> questions = questionRepository.findAll();
        return questions.get(number);
    }

}
