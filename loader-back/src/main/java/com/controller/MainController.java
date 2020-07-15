package com.controller;

import com.pojo.Customer;
import com.pojo.MessageToUsers;
import com.pojo.PojoOrdinarClassMapper;
import com.pojo.Porter;
import com.service.CustomerService;
import com.service.GlobalTelegramMessageSender;
import com.service.PorterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin()
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private GlobalTelegramMessageSender globalTelegramMessageSender;
    @Autowired
    private PorterService portserService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("all/porters")
    @ResponseBody
    public List<Porter> getShowPorters() {
        return portserService.findAll().stream()
                .map(PojoOrdinarClassMapper::porterToPorterPojoMapping)
                .collect(Collectors.toList());
    }

    @GetMapping("all/customers")
    @ResponseBody
    public List<Customer> getShowCustomers() {
        return customerService.findAll().stream()
                .map(PojoOrdinarClassMapper::customerToCustomerPojoMapping)
                .collect(Collectors.toList());
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
