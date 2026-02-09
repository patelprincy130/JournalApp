package com.example.journalApp.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoCriteriaTests {
    @Autowired
    private UserRepoCriteria userRepoCriteria;

    @Test
    @Disabled
    public void getUserByUserNameTest(){
        Assertions.assertNotNull(userRepoCriteria.getUsersByName());
    }

    @Test
    public void getUserForSATest(){
//        Assertions.assertNotNull(userRepoCriteria.getUserForSA()); //this will pass for empty [] list as well, when users=0
        Assertions.assertFalse(userRepoCriteria.getUserForSA().isEmpty());
    }
}
