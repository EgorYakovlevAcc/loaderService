package com.controller;

import com.model.user.Porter;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin()
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/delete/{id}")
    public ResponseEntity getDeleteUser(@PathVariable("id") Integer id){
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}/present")
    public ResponseEntity getPresentToUser(@PathVariable("id") Integer id){

        return ResponseEntity.ok(null);
    }
    @GetMapping("/{id}/present/check")
    public Porter getCheckPresentToUser(@PathVariable("id") Integer id){

        return null;
    }
}
