package com.example.journalApp.scheduler;

import com.example.journalApp.config.AppCache;
import com.example.journalApp.entity.JournalEntity;
import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.enums.Sentiment;
import com.example.journalApp.repository.UserRepoCriteria;
import com.example.journalApp.service.EmailService;
import com.example.journalApp.service.SentimentAnalysisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private AppCache appCache;

    //@Scheduled(cron = "0 0 9 * * SUN")   //@Scheduled(cron = "0 * * * * *")  -->every min
    public void sendEmail(){
        try{
            List<UserEntity> users=userRepoCriteria.getUserForSA();
            for(UserEntity user:users){
                List<JournalEntity> journalEntries=user.getJournalEntityList();
                List<Sentiment> sentiments=journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
                Map<Sentiment,Integer> sentimentCounts=new HashMap<>();
                for(Sentiment sentiment:sentiments){
                    if(sentiment!=null){
                        sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
                    }
                }
                Sentiment mostFrequentSentiment=null;
                int maxCount=0;
                for(Map.Entry<Sentiment,Integer> entry: sentimentCounts.entrySet()){
                    if(entry.getValue()>maxCount){
                        mostFrequentSentiment=entry.getKey();
                        maxCount=entry.getValue();
                    }
                }
                if(mostFrequentSentiment!=null){
                    emailService.sendEmail(user.getEmail(),"Summary of last 7 days",mostFrequentSentiment.toString());
                }

            }
        }catch (Exception e){
            log.error("Error sending email ",e);
        }

    }

    @Scheduled
    public void init(){
        appCache.init();
    }
}
