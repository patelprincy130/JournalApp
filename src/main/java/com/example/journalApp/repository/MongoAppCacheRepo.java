package com.example.journalApp.repository;

import com.example.journalApp.entity.MongoAppCacheEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoAppCacheRepo extends MongoRepository<MongoAppCacheEntity, ObjectId> {
}
