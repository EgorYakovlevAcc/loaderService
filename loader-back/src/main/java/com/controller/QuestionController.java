package com.controller;

import com.model.Question;
import com.pojo.Option;
import com.pojo.QuestionAndOptions;
import com.pojo.QuestionOptionsAnswer;
import com.service.OptionService;
import com.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/questions")
@CrossOrigin()
public class QuestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionService questionService;
    @Autowired
    private OptionService optionService;

    @GetMapping(value = {"/all/porters"})
    public String getQuestionsPorters(Model model) {
        List<Question> questions = questionService.findAllQuestionsForPorters();
        model.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping(value = {"/all/customers"})
    public String getQuestionsCustomers(Model model) {
        List<Question> questions = questionService.findAllQuestionsForCustomers();
        model.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping(value = {"/all/text"})
    @ResponseBody
    public List<QuestionOptionsAnswer> getQuestionsText(Model model) {
        return null;
    }

    @GetMapping(value = {"/add"})
    public String getAddQuestion(Model model) {
        System.out.println("getAddQuestion");
        if (Objects.isNull(model.getAttribute("questionAndOption"))) {
            QuestionAndOptions questionAndOptions = new QuestionAndOptions();
            model.addAttribute("questionAndOption", questionAndOptions);
            model.addAttribute("buttonValue", "createQuestion");
        }
        return "add_question";
    }

    @PostMapping(value = "/add")
    public ResponseEntity postAddQuestion(@RequestBody QuestionOptionsAnswer questionOptionsAnswer){
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/edit")
    @ResponseBody
    public QuestionOptionsAnswer getEditQuestion(Model model, @RequestParam("id") Integer questionId) {
        Question question = questionService.findQuestionById(questionId);
        QuestionOptionsAnswer questionOptionsAndAnswer = new QuestionOptionsAnswer();
        List<Option> optionsPojo = question.getOptions().stream()
                .map(option -> optionService.convertOptionModelToOptionPojo(option))
                .collect(Collectors.toList());
        questionOptionsAndAnswer.setOptions(optionsPojo);
        return questionOptionsAndAnswer;
    }

    @PostMapping(value = "/edit")
    public ResponseEntity postEditQuestion(@RequestBody QuestionOptionsAnswer questionOptionsAnswer) {
        System.out.println(questionOptionsAnswer.getId());
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/remove")
    public ResponseEntity editQuestion(Model model, @RequestParam("id") Integer questionId) {
        questionService.deleteQuestionById(questionId);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/attachment/upload/{questionId}")
    public ResponseEntity postAttachment(@RequestParam("image") MultipartFile imageFile, @PathVariable("questionId") Integer questionId){
        byte[] imageByte = new byte[0];
        try {
            imageByte = imageFile.getBytes();
            Question question = questionService.findQuestionById(questionId);
            questionService.saveQuestionWithImageContent(question, imageByte);
            return ResponseEntity.ok(null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok("IMPOSSIBLE TO SAVE IMAGE");
        }

    }
}
