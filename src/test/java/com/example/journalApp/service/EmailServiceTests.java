package com.example.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    EmailService emailService;

    private String email;
    @Test
    public void sendEmailTest(){
        emailService.sendEmail("patelprincy130@gmail.com","Done","JSN");
    }
}
