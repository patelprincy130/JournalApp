package com.example.journalApp.controller;

import com.example.journalApp.entity.JournalEntity;
import com.example.journalApp.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<JournalEntity> getAll(){
        return journalService.getAll();
    }

    @PostMapping
    public boolean createJournal(@RequestBody JournalEntity journal){
        journalService.saveEntry(journal);
        return true;
    }

    @GetMapping("id/{id}")
    public JournalEntity getJournal(@PathVariable String id){
        return journalService.getJournal(id);
    }

    @PutMapping("id/{id}")
    public JournalEntity updateJournal(@PathVariable String id,@RequestBody JournalEntity journal){
        return journalService.updateJournal(id,journal);
    }

    @DeleteMapping("id/{id}")
    public JournalEntity deleteJournal(@PathVariable String id){
        return journalService.deleteJournal(id);
    }
}
