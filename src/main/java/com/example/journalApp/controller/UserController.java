package com.example.journalApp.controller;

import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping
//    public List<UserEntity> getUser(){
//        return userService.getUsers();
//    }

//    @PostMapping  -->this can be accessed without authentication
//    public void createUser(@RequestBody UserEntity user){
//        userService.saveNewUser(user);
//    }

    //@PutMapping("{userName}") //we dont have to pass username in path, once the user is authenticated then username and password is automatically stored in holder
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();//user is already authenticated
        String userName=authentication.getName();
        UserEntity userInDB=userService.findByUsername(userName);
//        if(userInDB!=null){
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userService.saveNewUser(userInDB);
        //}
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public void deleteUserByUserName(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();//user is already authenticated
        String userName=authentication.getName();
        userService.deleteByUserName(userName);
    }

}
