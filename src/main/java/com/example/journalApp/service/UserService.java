package com.example.journalApp.service;

import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public void createUser(UserEntity user) {
        userRepository.save(user);
    }

    public UserEntity findByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }

    public Optional<UserEntity> getUserByID(String id){
        return userRepository.findById(id);
    }

    public void deleteByID(String id){
        userRepository.deleteById(id);
    }
}
