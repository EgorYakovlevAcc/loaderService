package com.controller;

import com.model.ScoreRangesMessenger;
import com.model.user.Porter;
import com.pojo.MessageToUsers;
import com.service.GlobalTelegramMessageSender;
import com.service.UserService;
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
@CrossOrigin()
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private GlobalTelegramMessageSender globalTelegramMessageSender;

    @GetMapping("all/users")
    @ResponseBody
    public List<Porter> getShowUsers(Model model) {
        return null;
    }

    @GetMapping(value = {"/", "/index"})
    public String getIndex() {
        return "forward:/index.html";
    }

    @PostMapping(value = "/send/global/message")
    public ResponseEntity postSendMessage(@RequestBody MessageToUsers messageToUsers){
        globalTelegramMessageSender.sendGlobalMessage(messageToUsers.getText(), messageToUsers.getMinScore(), messageToUsers.getMaxScore());
        return ResponseEntity.ok(null);
    }
}
