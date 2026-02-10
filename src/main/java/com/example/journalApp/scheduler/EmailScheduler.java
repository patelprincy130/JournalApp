package com.example.journalApp.scheduler;

import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.repository.UserRepoCriteria;
import com.example.journalApp.service.EmailService;
import com.example.journalApp.service.SentimentAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EmailScheduler {

    @Autowired
    private UserRepoCriteria userRepoCriteria;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;


    @Scheduled(cron = "0 0 9 * * SUN")   //@Scheduled(cron = "0 * * * * *")  -->every min
    public void sendEmail(){
        try{
            List<UserEntity> users=userRepoCriteria.getUserForSA();
            for(UserEntity user:users){
                List<String> journals=user.getJournalEntityList().stream().map(x->x.getStory()).filter(x->x.contains("good")).collect(Collectors.toList());
                String entries=String.join(" ",journals);
                String sentiment=sentimentAnalysisService.result(entries);
                emailService.sendEmail(user.getEmail(),"Summary of last 7 days",sentiment);
            }
        }catch (Exception e){
            log.error("Error sending email ",e);
        }

    }
}
