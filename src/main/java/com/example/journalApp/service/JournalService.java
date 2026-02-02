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
        JournalEntity journal1=repo.findById(id).get();
        journal1.setId(journal.getId());
        journal1.setStory(journal.getStory());
        journal1.setTitle(journal.getTitle());
        return repo.save(journal1);
    }

    public JournalEntity deleteJournal(String id) {
        JournalEntity journal=repo.findById(id).get();
        repo.delete(journal);
        return journal;
    }


}
