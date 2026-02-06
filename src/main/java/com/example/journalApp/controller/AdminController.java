package com.example.journalApp.controller;


import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> users=userService.getAll();
        if(users!=null && !users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void addNewAdmin(@RequestBody UserEntity user){
        userService.saveAdmin(user);
    }

}
