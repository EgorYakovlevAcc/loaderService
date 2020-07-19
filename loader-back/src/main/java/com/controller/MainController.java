package com.controller;

import com.pojo.*;
import com.service.AdministratorService;
import com.service.CustomerService;
import com.service.GlobalTelegramMessageSender;
import com.service.PorterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Autowired
    private AdministratorService administratorService;

    @GetMapping("all/porters")
    @ResponseBody
    public List<Porter> getShowPorters() {
        return portserService.findAll().stream()
                .map(PojoOrdinarClassMapper::porterToPorterPojoMapping)
                .collect(Collectors.toList());
    }

    @GetMapping("all/admins")
    @ResponseBody
    public List<Administrator> getShowAdmins() {
        return administratorService.findAll().stream()
                .map(PojoOrdinarClassMapper::administatorToAdministratorPojoMapping)
                .collect(Collectors.toList());
    }

    @GetMapping("all/customers")
    @ResponseBody
    public List<Customer> getShowCustomers() {
        return customerService.findAll().stream()
                .map(PojoOrdinarClassMapper::customerToCustomerPojoMapping)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/remove/porter")
    public ResponseEntity removePorter(@RequestParam("id") Integer porterId) {
        portserService.deletePorterById(porterId);
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/remove/admin")
    public ResponseEntity removeAdminstrator(@RequestParam("id") Integer adminId) {
        administratorService.deleteAdminById(adminId);
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/remove/customer")
    public ResponseEntity removeCustomer(@RequestParam("id") Integer customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok(null);
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
