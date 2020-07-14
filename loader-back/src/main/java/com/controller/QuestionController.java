package com.controller;

import com.pojo.Question;
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

@Controller
@RequestMapping("/questions")
@CrossOrigin()
public class QuestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    private QuestionService questionService;

    @GetMapping(value = {"/all/porters"})
    public String getQuestionsPorters(Model model) {
        List<com.model.Question> questions = questionService.findAllQuestionsForPorters();
        model.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping(value = {"/all/customers"})
    public String getQuestionsCustomers(Model model) {
        List<com.model.Question> questions = questionService.findAllQuestionsForCustomers();
        model.addAttribute("questions", questions);
        return "questions";
    }

    @GetMapping(value = {"/add"})
    public String getAddQuestion(Model model) {
        System.out.println("getAddQuestion");
        if (Objects.isNull(model.getAttribute("questionAndOption"))) {
            Question questionAndOptions = new Question();
            model.addAttribute("questionAndOption", questionAndOptions);
            model.addAttribute("buttonValue", "createQuestion");
        }
        return "add_question";
    }

    @PostMapping(value = "/add")
    public ResponseEntity postAddQuestion(@RequestBody Question question){
        questionService.createQuestionForTypeByPojo(question);
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/edit")
    @ResponseBody
    public Question getEditQuestion(Model model, @RequestParam("id") Integer questionId) {
        com.model.Question question = questionService.findQuestionById(questionId);
        Question questionPojo = new Question();
        return questionPojo;
    }

    @PostMapping(value = "/edit")
    public ResponseEntity postEditQuestion(@RequestBody Question questionPojo) {
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
            com.model.Question question = questionService.findQuestionById(questionId);
            questionService.saveQuestionWithImageContent(question, imageByte);
            return ResponseEntity.ok(null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok("IMPOSSIBLE TO SAVE IMAGE");
        }

    }
}
