package com.example.journalApp.repository;

import com.example.journalApp.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity,String> {
    UserEntity findByUserName(String userName);
}
