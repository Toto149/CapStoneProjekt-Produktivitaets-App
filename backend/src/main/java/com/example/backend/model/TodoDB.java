package com.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "todo")
public record TodoDB(@Id
                     String id,
                     String title,
                     String description,
                     String startDate,
                     String deadline,
                     String gradeOfImportance,
                     String timeToComplete) {
}
