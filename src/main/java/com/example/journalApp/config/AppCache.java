package com.example.journalApp.config;

import com.example.journalApp.entity.MongoAppCacheEntity;
import com.example.journalApp.repository.MongoAppCacheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        API;
    }

    @Autowired
    private MongoAppCacheRepo repo;

    public Map<String,String> appCache;

    @PostConstruct
    public void init(){
        appCache=new HashMap<>();
        List<MongoAppCacheEntity> res=repo.findAll();
        for(MongoAppCacheEntity n:res){
            appCache.put(n.getKey(),n.getValue());
        }

    }
}
