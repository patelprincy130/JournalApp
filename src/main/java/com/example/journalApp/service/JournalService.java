package com.example.journalApp.service;

import com.example.journalApp.entity.JournalEntity;
import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.List;

@Service
public class JournalService {

    @Autowired
    JournalRepository repo;

    @Autowired
    UserService userService;

    @Transactional
    public void saveEntry(JournalEntity journal,String userName){
        try{
            UserEntity userIndb = userService.findByUsername(userName);
            JournalEntity saved=repo.save(journal);
            userIndb.getJournalEntityList().add(saved);
            //userIndb.setUserName(null); //if any exception occurs here then user wont be saved, journal will be saved but associated user won't be saved --> data inconsistency --> Transactional
            userService.createUser(userIndb);
        } catch (Exception e) {
            e.printStackTrace();
            //if this below line is commented then spring wont roll back.
            throw new RuntimeException(e);
        }
    }
    /*
    * 🔁 How Spring transactions actually work
Spring rolls back ONLY IF:
A RuntimeException or Error is thrown
AND it is not caught inside the method
* */

    public void saveEntry(JournalEntity journal) {
        repo.save(journal);
    }

    public List<JournalEntity> getAll(){
        return repo.findAll();
    }

    public JournalEntity getJournal(String id) {
        return repo.findById(id).get();
    }

//    public JournalEntity updateJournal(String id, JournalEntity journal) {
//        JournalEntity old=repo.findById(id).get();
//        if(old!=null){
//            old.setTitle( journal.getTitle()!=null && !journal.getTitle().equals("") ? journal.getTitle():old.getTitle());
//            old.setStory(journal.getStory()!=null && !journal.getTitle().equals("") ? journal.getStory() : old.getStory());
//        }
//        return repo.save(old);
//    }

    public JournalEntity deleteJournal(String id, String userName) {
        UserEntity userDB=userService.findByUsername(userName);
        userDB.getJournalEntityList().removeIf(e->e.getId().equals(id));
        JournalEntity journal=repo.findById(id).get();
        repo.delete(journal);
        userService.createUser(userDB);
        return journal;
    }



}
