package com.example.journalApp.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "appcacheconfig")
@Getter
@Setter
public class MongoAppCacheEntity {
   private String key;
   private String value;
}
