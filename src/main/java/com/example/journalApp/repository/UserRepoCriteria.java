package com.example.journalApp.repository;

import com.example.journalApp.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepoCriteria {

    @Autowired
    private MongoTemplate mongoTemplate;

    //for test
    public List<UserEntity> getUsersByName(){
        Query query=new Query();
        query.addCriteria(Criteria.where("userName").is("Lee"));
        List<UserEntity> users=mongoTemplate.find(query,UserEntity.class);
        return users;
    }

    public List<UserEntity> getUserForSA(){ //getting users with email and sentimentAnys is true
        Query query=new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));
        //query.addCriteria(Criteria.where("email").ne(null).ne("")); //ne-not equal, gte-greaterthanequal
        query.addCriteria(Criteria.where("SentimentAnalysis").is(true));
        //----other---
       // query.addCriteria(Criteria.where("roles").in("USER","ADMIN")); //woring with array
       // query.addCriteria(Criteria.where("sentimentAnalysis").type(JsonSchemaObject.Type.BsonType.BOOLEAN));//with type

//        Criteria criteria=new Criteria();
        //query.addCriteria(criteria.andOperator(Criteria.where("email").exists(true),Criteria.where("sentimentAnalysis").is(true),Criteria.where("email").ne(null).ne("")));

        List<UserEntity> users=mongoTemplate.find(query,UserEntity.class);
        return users;
    }

}
