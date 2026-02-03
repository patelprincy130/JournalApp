package com.example.journalApp.controller;

import com.example.journalApp.entity.JournalEntity;
import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.service.JournalService;
import com.example.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
//Second
@RestController
@RequestMapping("journals")
public class JournalController {

    @Autowired
    JournalService journalService;

    @Autowired
    UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<JournalEntity>> getAllJournalsForUser(@PathVariable String userName){
        UserEntity userInDb=userService.findByUsername(userName);
        List<JournalEntity> journals= userInDb.getJournalEntityList();
        if(journals!=null && !journals.isEmpty()){
            return new ResponseEntity<>(journals, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntity> createJournal(@RequestBody JournalEntity journal, @PathVariable String userName){
        try {
            journalService.saveEntry(journal,userName);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntity> getJournal(@PathVariable String id){
        JournalEntity journal= journalService.getJournal(id);
        if(journal!=null){
            return new ResponseEntity<>(journalService.getJournal(id),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{id}/{userName}")
    public ResponseEntity<JournalEntity> updateJournal(@PathVariable String id,@RequestBody JournalEntity journal, @PathVariable String userName){
        JournalEntity old=journalService.getJournal(id);
        if(old!=null){
            old.setTitle( journal.getTitle()!=null && !journal.getTitle().equals("") ? journal.getTitle():old.getTitle());
            old.setStory(journal.getStory()!=null && !journal.getTitle().equals("") ? journal.getStory() : old.getStory());
            journalService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}/{userName}")
    public ResponseEntity<?> deleteJournal(@PathVariable String id, @PathVariable String userName){
        journalService.deleteJournal(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
