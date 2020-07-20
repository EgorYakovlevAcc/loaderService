package com.service.impl;

import com.bot.BotModel;
import com.model.Question;
import com.model.UserType;
import com.model.user.BotUser;
import com.model.user.Customer;
import com.model.user.Porter;
import com.repo.CustomerRepository;
import com.repo.PorterRepository;
import com.repo.QuestionRepository;
import com.service.CustomerService;
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
    @Autowired
    private PorterRepository porterRepository;
    @Autowired
    private CustomerRepository customerRepository;

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
    public Question getNextQuestionForPorter(Porter porter) {
        Integer currentQuestionNumber = porter.getCurrentQuestionNum();
        Integer porterQuestionAmount = questionRepository.countAllByUserType(UserType.PORTER);
        if (currentQuestionNumber >= porterQuestionAmount) {
            return null;
        }
        Integer questionNumber = currentQuestionNumber == -1 ? 0 : currentQuestionNumber;
        Question question = questionRepository.findQuestionByQuestionNumberAndUserType(questionNumber, UserType.PORTER);
        porter.setAskingQuestions(true);
        porter.setCurrentQuestionNum(questionNumber + 1);
        porterRepository.save(porter);
        return question;
    }

    @Override
    public Question getNextQuestionForCustomer(Customer customer) {
        Integer customerQuestionAmount = questionRepository.countAllByUserType(UserType.CUSTOMER);
        Integer currentQuestionNumber = customer.getCurrentQuestionNum();
        if (currentQuestionNumber >= customerQuestionAmount) {
            return null;
        }
        Integer questionNumber = currentQuestionNumber == -1 ? 0 : currentQuestionNumber;
        Question question = questionRepository.findQuestionByQuestionNumberAndUserType(questionNumber, UserType.CUSTOMER);
        customer.setCurrentQuestionNum(questionNumber + 1);
        customer.setAskingQuestions(true);
        customerRepository.save(customer);
        return question;
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
        UserType userType = questionPojo.getUserType().equals(BotModel.UserTypeStr.USER_TYPE_CUSTOMER) ? UserType.CUSTOMER : UserType.PORTER;
        Integer newQuestionNum = questionRepository.countAllByUserType(userType);
        Question question = new Question();
        question.setText(questionPojo.getContent());
        question.setUserType(userType);
        question.setQuestionNumber(newQuestionNum);
        questionRepository.save(question);
    }
}
