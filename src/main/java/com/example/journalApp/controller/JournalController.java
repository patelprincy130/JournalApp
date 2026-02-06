package com.example.journalApp.controller;

import com.example.journalApp.entity.JournalEntity;
import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.service.JournalService;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Second
@RestController
@RequestMapping("journals")
public class JournalController {

    @Autowired
    JournalService journalService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAllJournalsForUser(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        UserEntity userInDb=userService.findByUsername(userName);
        List<JournalEntity> journals= userInDb.getJournalEntityList();
        if(journals!=null && !journals.isEmpty()){
            return new ResponseEntity<>(journals, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntity> createJournal(@RequestBody JournalEntity journal){
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            journalService.saveEntry(journal,userName);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntity> getJournal(@PathVariable String id){ //get by id
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        UserEntity user=userService.findByUsername(userName);
        List<JournalEntity> list=user.getJournalEntityList().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(!list.isEmpty()){
            JournalEntity journal= journalService.getJournal(id);
            return new ResponseEntity<>(journal,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntity> updateJournal(@PathVariable String id,@RequestBody JournalEntity journal){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        UserEntity user=userService.findByUsername(userName);
        List<JournalEntity> list=user.getJournalEntityList().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(!list.isEmpty()){
            JournalEntity old=journalService.getJournal(id);
            if(old!=null){
                old.setTitle( journal.getTitle()!=null && !journal.getTitle().isEmpty() ? journal.getTitle():old.getTitle());
                old.setStory(journal.getStory()!=null && !journal.getTitle().isEmpty() ? journal.getStory() : old.getStory());
                journalService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable String id){
        boolean removed=false;
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        UserEntity user=userService.findByUsername(userName);
        List<JournalEntity> list=user.getJournalEntityList().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
        if(!list.isEmpty()){
            removed=journalService.deleteJournal(id, userName);
        }
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
