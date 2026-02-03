package com.example.journalApp.controller;

import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserEntity> getUser(){
        return userService.getUsers();
    }

    @PostMapping
    public void createUser(@RequestBody UserEntity user){
        userService.createUser(user);
    }

    @PutMapping("{username}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user,@PathVariable String username){
        UserEntity userInDB=userService.findByUsername(username);
        if(userInDB!=null){
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userService.createUser(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
