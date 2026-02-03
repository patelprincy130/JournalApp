package com.example.journalApp.service;

import com.example.journalApp.entity.JournalEntity;
import com.example.journalApp.repository.JournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class JournalService {

    @Autowired
    JournalRepository repo;

    public JournalEntity saveEntry(JournalEntity journal){
        return repo.save(journal);
    }

    public List<JournalEntity> getAll(){
        return repo.findAll();
    }

    public JournalEntity getJournal(String id) {
        return repo.findById(id).get();
    }

    public JournalEntity updateJournal(String id, JournalEntity journal) {
        JournalEntity old=repo.findById(id).get();
        if(old!=null){
            old.setTitle( journal.getTitle()!=null && !journal.getTitle().equals("") ? journal.getTitle():old.getTitle());
            old.setStory(journal.getStory()!=null && !journal.getTitle().equals("") ? journal.getStory() : old.getStory());
        }
        return repo.save(old);
    }

    public JournalEntity deleteJournal(String id) {
        JournalEntity journal=repo.findById(id).get();
        repo.delete(journal);
        return journal;
    }


}
