package com.example.journalApp.entity;

import com.example.journalApp.enums.Sentiment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entity")
@Data
@NoArgsConstructor
public class JournalEntity {
    @Id
    private String id;
    @NonNull
    private String title;
    private String story;
    private LocalDateTime date;
    private Sentiment sentiment;
}
