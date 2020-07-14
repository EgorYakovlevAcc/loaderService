package com.service.impl;

import com.bot.BotModel;
import com.model.Question;
import com.model.UserType;
import com.repo.QuestionRepository;
import com.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    public List<Question> findAllQuestionsForPorters() {
        return questionRepository.findQuestionsByUserType(UserType.PORTER);
    }

    @Override
    public List<Question> findAllQuestionsForCustomers() {
        return questionRepository.findQuestionsByUserType(UserType.CUSTOMER);
    }

    @Override
    public void createQuestionForTypeByPojo(com.pojo.Question questionPojo) {
        Question question = new Question();
        question.setText(questionPojo.getContent());
        question.setUserType(questionPojo.getUserType().equals(BotModel.UserTypeStr.USER_TYPE_CUSTOMER) ? UserType.CUSTOMER : UserType.PORTER);
        questionRepository.save(question);
    }
}
